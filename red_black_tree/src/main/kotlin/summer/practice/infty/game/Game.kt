package summer.practice.infty.game

import summer.practice.infty.game.creatures.*
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent
import summer.practice.infty.game.rooms.EmptyRoom
import summer.practice.infty.game.rooms.Room
import summer.practice.infty.rbt.RedBlackTreeGame
import kotlin.random.Random

class Game {
    private val tree = RedBlackTreeGame<Int>()
    val player = Player(this)
    var room: Room = EmptyRoom()
    var creature: Creature = EmptyCreature()
    var event: RoomEvent = EmptyEvent()
    private var cur_stage = Stages.WAY
    private var adds = 0
    private var dels = 0
    private var creature_description = ""
    private var event_description = ""
    private var way_description = ""

    fun next(){
        cur_stage = when(cur_stage){
            Stages.CREATURE ->{
                if(event.isVisible(player.perception)){
                    Stages.EVENT
                }else{
                    changeTree()
                    Stages.WAY
                }
            }
            Stages.EVENT -> Stages.WAY
            Stages.WAY -> {
                creature = room.creature
                event = room.event
                player.in_fight = creature.in_battle
                creature_description = creature.description
                event_description = event.description
                val left_right = tree.getLeftRightKeys(player.cur_room)
                way_description = if(left_right.first == null && left_right.second == null) "You win!"
                                  else "You can go "
                way_description += when{
                    left_right.first == null && left_right.second == null -> ""
                    left_right.first == null -> "right."
                    left_right.second == null -> "left."
                    left_right.first != null && left_right.second != null -> "left or right"
                    else -> ""
                }
                Stages.CREATURE
            }
        }
    }

    fun buy(index: Int){
        if(cur_stage != Stages.CREATURE) return
        if(creature !is Trader || index >= 3 ||
           player.getItemsCountInInventory() >= player.getInventoryCapacity()) return
        (creature as? Trader)?.buy(index, player)
        adds += 1
    }

    fun sold(item_index: Int, active: Boolean = false){
        if(cur_stage != Stages.CREATURE) return
        if(creature !is Trader) return
        if(active || item_index in 0 until player.getActiveCapacity()) player.soldActiveItem(item_index)
        else if(item_index !in 0 until player.getInventoryCapacity()) player.soldInventoryItem(item_index)
    }

    fun pay(){
        if(cur_stage != Stages.CREATURE) return
        val b = creature as? Bandit ?: return
        if(player.coins >= b.pay_cost){
            player.coins -= b.pay_cost
            adds += 2
            event_description = "You bought off the Bandit. $event_description"
            next()
        }
    }

    fun toFight(){
        if(cur_stage != Stages.CREATURE) return
        val b = creature as? Bandit ?: return
        b.in_battle = true
        player.in_fight = true
        dels += 2
    }

    fun fight(by_magic: Boolean){
        if(cur_stage != Stages.CREATURE) return
        while(player.health > 0 && creature.health > 0){
            player.Attack(creature)
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        adds += if(by_magic) 1 else 2
        dels += if(by_magic) 2 else 1
        player.in_fight = false
        if(player.health <= 0){
            end()
        }else{
            event_description = creature.win_description + " " + event_description
            next()
        }
    }

    fun actEvent(){
        if(event.canAct(player)){
            adds += 1
            changeTree()
            event.actWithPlayer(player)
            next()
        }
    }

    fun nextRoom(left: Boolean){
        val left_right = tree.getLeftRightKeys(player.cur_room)
        if(left_right.first == null && left_right.second == null) win()
        if(left){
            player.cur_room = if(left_right.first != null) left_right.first!!
                              else left_right.second!!
        }else{
            player.cur_room = if(left_right.second != null) left_right.second!!
                              else left_right.first!!
        }
        room = tree.find(player.cur_room)!!
    }

    fun movePlayerOnDepth(){
        player.cur_room = tree.getRandomKeyOnDepth(tree.getKeyDepth(player.cur_room))
                ?: throw RuntimeException("Null key in teleport")
        room = tree.find(player.cur_room)!!
        cur_stage = Stages.WAY
        adds += 2
        dels += 4
        next()
    }

    fun addNextCreatureDescription(){
        val keys = tree.getLeftRightKeys(player.cur_room)
        var next_creature: Creature?
        if(keys.first != null){
            next_creature = tree.find(keys.first!!)?.creature
            val type = when{
                (next_creature is Trader) -> "Trader"
                (next_creature is Golem) -> "Golem"
                (next_creature is Bandit) -> "Bandit"
                (next_creature is Dragon) -> "Dragon"
                else -> "Unknown"
            }
            way_description += " On the left side is the $type with damage " +
                               "equal to ${next_creature?.damage ?: 0}."
        }
        if(keys.second != null){
            next_creature = tree.find(keys.second!!)?.creature
            val type = when{
                (next_creature is Trader) -> "Trader"
                (next_creature is Golem) -> "Golem"
                (next_creature is Bandit) -> "Bandit"
                (next_creature is Dragon) -> "Dragon"
                else -> "Unknown"
            }
            way_description += " On the right side is the $type with damage " +
                               "equal to ${next_creature?.damage ?: 0}."
        }
    }

    fun swapPlayerItems(index1: Int, index2: Int){
        player.swapInventoryItems(index1, index2)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
    }

    fun useItem(index: Int){
        player.useItem(index)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
    }

    fun start(){
        generateEasyTree()
        player.cur_room = tree.iterator().getKey()
        room = tree.find(player.cur_room)!!
        cur_stage = Stages.WAY
    }

    private fun end(){
        TODO("End Game")
    }

    private fun win(){
        TODO("Win Game")
    }

    fun increaseNumberOfRoomAdds(inc: Int){
        if(inc < 0) return
        adds += inc
    }

    fun increaseNumberOfRoomDels(inc: Int){
        if(inc < 0) return
        dels += inc
    }

    private fun changeTree(){
        val key_min = player.cur_room - 100
        val key_max = player.cur_room + 100
        var arr = Array<Int>(adds * 5){
            var key: Int
            do{
                key = Random.nextInt(key_min, key_max)
            }while(key == player.cur_room)
            key
        }
        tree.insertRooms(*arr)
        arr = Array<Int>(dels * 3){
            var key: Int
            do{
                key = Random.nextInt(key_min, key_max)
            }while(key == player.cur_room)
            key
        }
        tree.deleteRooms(*arr)
        adds = 0
        dels = 0
    }

    private fun generateEasyTree(){
        tree.clear()
        val keys = Array<Int>(Random.nextInt(50, 80)){Random.nextInt(0, 1000)}
        tree.insertRooms(*keys)
    }

    fun getDescription(){
        when(cur_stage){
            Stages.CREATURE -> creature_description
            Stages.EVENT -> event_description
            Stages.WAY -> way_description
        }
    }
}

private enum class Stages{
    CREATURE,
    EVENT,
    WAY
}
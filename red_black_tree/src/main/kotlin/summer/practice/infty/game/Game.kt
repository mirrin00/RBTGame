package summer.practice.infty.game

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionEvent
import summer.practice.infty.actions.ActionNext
import summer.practice.infty.actions.ActionNextRoom
import summer.practice.infty.controllers.ViewController
import summer.practice.infty.game.creatures.*
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent
import summer.practice.infty.game.generators.Generator
import summer.practice.infty.game.items.ItemType
import summer.practice.infty.game.rooms.EmptyRoom
import summer.practice.infty.game.rooms.Room
import summer.practice.infty.rbt.RedBlackTreeGame
import kotlin.random.Random

class Game(var view: ViewController<Int>? = null) {
    private val tree = RedBlackTreeGame<Int>()
    private var player = Player(this)
    var room: Room = EmptyRoom()
    var creature: Creature = EmptyCreature()
    var event: RoomEvent = EmptyEvent()
    private var game_active = false
    private var cur_stage = Stages.WAY
    private var adds = 0
    private var dels = 0
    private var creature_description = ""
    private var event_description = ""
    private var way_description = ""
    private var description_action_before = ""

    fun next(){
        cur_stage = when(cur_stage){
            Stages.CREATURE ->{
                description_action_before = creature.win_description
                if(event.isVisible(player.perception)){
                    Stages.EVENT
                }else{
                    changeTree()
                    Stages.WAY
                }
            }
            Stages.EVENT -> {
                description_action_before = ""
                Stages.WAY
            }
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
                view?.updateLocalTree(tree, player.cur_room)
                Stages.CREATURE
            }
        }
        view?.update()
    }

    fun buy(index: Int){
        if(cur_stage != Stages.CREATURE || !game_active) return
        if(creature !is Trader || index >= 3 ||
           player.getItemsCountInInventory() >= player.getInventoryCapacity()) return
        (creature as? Trader)?.buy(index, player)
        adds += 1
        view?.update()
    }

    fun sold(item_index: Int, active: Boolean = false){
        if(!game_active) return
        if(cur_stage == Stages.CREATURE && creature is Trader) {
            if (active && item_index in 0 until player.getActiveCapacity()) player.soldActiveItem(item_index)
            else if (item_index in 0 until player.getInventoryCapacity()) player.soldInventoryItem(item_index)
        }else{
            if (active && item_index in 0 until player.getActiveCapacity()) player.removeActiveItem(item_index)
            else if (item_index in 0 until player.getInventoryCapacity()) player.removeInventoryItem(item_index)
        }
        view?.update()
    }

    fun pay(){
        if(cur_stage != Stages.CREATURE || !game_active) return
        val b = creature as? Bandit ?: return
        if(player.coins >= b.pay_cost){
            player.coins -= b.pay_cost
            adds += 2
            event_description = "You bought off the Bandit. $event_description"
            next()
        }
        view?.update()
    }

    fun toFight(){
        if(cur_stage != Stages.CREATURE || !game_active) return
        val b = creature as? Bandit ?: return
        b.in_battle = true
        player.in_fight = true
        dels += 2
        view?.update()
    }

    fun fight(by_magic: Boolean){
        if(cur_stage != Stages.CREATURE || !game_active) return
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
            for(i in 1..3){
                if(player.getItemsCountInInventory() >= player.getInventoryCapacity()) break
                player.addItem(Generator.generateItem(room.deep_level))
            }
            player.coins += (creature as? Bandit)?.coins ?: 0
            next()
        }
        view?.update()
    }

    fun actEvent(){
        if(!game_active) return
        if(event.canAct(player)){
            adds += 1
            changeTree()
            event.actWithPlayer(player)
            next()
        }
        view?.update()
    }

    fun nextRoom(left: Boolean){
        if(!game_active) return
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
        next()
        view?.update()
    }

    fun movePlayerOnDepth(){
        if(!game_active) return
        player.cur_room = tree.getRandomKeyOnDepth(tree.getKeyDepth(player.cur_room))
                ?: throw RuntimeException("Null key in teleport")
        room = tree.find(player.cur_room)!!
        cur_stage = Stages.WAY
        adds += 2
        dels += 4
    }

    fun addNextCreatureDescription(){
        if(!game_active) return
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
        next()
        view?.update()
    }

    fun swapPlayerItems(index1: Int, index2: Int){
        if(!game_active) return
        player.swapInventoryItems(index1, index2)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
        view?.update()
    }

    fun useItem(index: Int){
        if(!game_active) return
        player.useItem(index)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
        view?.update()
    }

    fun start(){
        game_active = true
        player = Player(this)
        player.addItem(Generator.generateItem(0, ItemType.WEAPON))
        player.addItem(Generator.generateItem(0, ItemType.MAGIC))
        player.addItem(Generator.generateItem(0, ItemType.ARMOR))
        player.addItem(Generator.generateItem(0, ItemType.HEALTH_POTION))
        generateEasyTree()
        player.cur_room = tree.iterator().getKey()
        room = tree.find(player.cur_room)!!
        cur_stage = Stages.WAY
        view?.updateLocalTree(tree, player.cur_room)
        view?.updateTree(tree)
        view?.update()
    }

    private fun end(){
        player.reset()
        game_active = false
        view?.youDied()
    }

    private fun win(){
        player.reset()
        game_active = false
        view?.win()
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
        view?.updateTree(tree)
        view?.updateLocalTree(tree, player.cur_room)
    }

    private fun generateEasyTree(){
        tree.clear()
        val keys = Array<Int>(Random.nextInt(50, 80)){Random.nextInt(0, 1000)}
        tree.insertRooms(*keys)
    }

    // <------------------------>
    // Methods for ViewController

    fun getDescription() = when(cur_stage){
            Stages.CREATURE -> creature_description
            Stages.EVENT -> "$description_action_before $event_description"
            Stages.WAY -> "$description_action_before $way_description"
        }

    fun getHealth() = player.health

    fun getCoins() = player.coins

    fun getMagic() = player.magic

    fun getAttributes() = "STRN\n${player.strength}\n\nDXTR\n${player.dexterity}\n\nINTL\n${player.intelligence}\n\nPRCT\n${player.perception}"

    fun getActions() = if(!game_active) ArrayList<Action>() else when(cur_stage){
        Stages.CREATURE -> creature.getActions(player)
        Stages.EVENT -> {
            val actions = ArrayList<Action>()
            if(event.canAct(player)) actions.add(ActionEvent(true, event.getActionDescription(), event.getUseDescription()))
            else actions.add(ActionEvent(false, event.getRequirements()))
            actions.add(ActionNext(true, "Move on"))
            actions
        }
        Stages.WAY -> {
            val actions = ArrayList<Action>()
            val keys = tree.getLeftRightKeys(player.cur_room)
            if(keys.first != null) actions.add(ActionNextRoom(true, true, "Go to the left"))
            if(keys.second != null) actions.add(ActionNextRoom(false, true, "Go to the right"))
            actions
        }
    }

    fun getInventory() = player.getInventory()

    fun getActiveItems() = player.getActiveItems()
}

private enum class Stages{
    CREATURE,
    EVENT,
    WAY
}
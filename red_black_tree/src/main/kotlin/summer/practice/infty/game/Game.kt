package summer.practice.infty.game

import javafx.beans.binding.BooleanExpression
import summer.practice.infty.game.creatures.Bandit
import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.creatures.EmptyCreature
import summer.practice.infty.game.creatures.Trader
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
    private var depth_level = 0
    private var adds = 0
    private var dels = 0

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
                Stages.CREATURE
            }
        }
    }

    fun buy(index: Int){
        if(cur_stage != Stages.CREATURE) return
        if(creature !is Trader || index >= 3 ||
           player.getItemsCountInInventory() >= player.getInventoryCapacity()) return
        (creature as? Trader)?.buy(index, player)
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
            next()
        }
    }

    fun toFight(){
        if(cur_stage != Stages.CREATURE) return
        val b = creature as? Bandit ?: return
        b.in_battle = true
        player.in_fight = true
    }

    fun fight(by_magic: Boolean){
        if(cur_stage != Stages.CREATURE) return
        while(player.health > 0 && creature.health > 0){
            player.Attack(creature)
            creature.Attack(player)
            if(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
        else next()
    }

    fun actEvent(){
        if(event.canAct(player)){
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

    fun start(){
        TODO("Start game")
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

    // Debug method
    fun debugStageDescription() = when(cur_stage){
        Stages.CREATURE -> "It's description of creature stage"
        Stages.EVENT -> "It's description of event"
        Stages.WAY -> "It's description of ways"
    }
}

private enum class Stages{
    CREATURE,
    EVENT,
    WAY
}
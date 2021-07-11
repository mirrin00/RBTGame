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

class Game {
    val tree = RedBlackTreeGame<Int>()
    val player = Player(this)
    var room: Room = EmptyRoom()
    var creature: Creature = EmptyCreature()
    var event: RoomEvent = EmptyEvent()
    private var cur_stage = Stages.WAY
    private var depth_level = 0

    fun next(){
        cur_stage = when(cur_stage){
            Stages.CREATURE ->{
                if(event.isVisible(player.perception)) Stages.EVENT
                else Stages.WAY
            }
            Stages.EVENT -> Stages.WAY
            Stages.WAY -> Stages.CREATURE
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
            event.actWithPlayer(player)
            next()
        }
    }

    fun nextRoom(left: Boolean){

    }

    fun start(){
        TODO("Start game")
    }

    private fun end(){
        TODO("End Game")
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
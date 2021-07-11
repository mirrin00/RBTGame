package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player
import summer.practice.infty.game.getHealthDamageOfElemnts
import summer.practice.infty.game.getRatioFromElemets

interface Creature {
    val element: Element
    var health: Int
    val damage: Int
    var in_battle: Boolean

    fun Attack(player: Player){
        player.health -= (damage * if(getRatioFromElemets(element, player.getArmorElement()) == 0.0) 1.0 else 1.5).toInt()
        health -= (damage * getHealthDamageOfElemnts(element, player.getArmorElement())).toInt()
    }

    fun getDamageRatio(magic: Boolean = false): Double

    fun getActions(): ArrayList<Action>
}
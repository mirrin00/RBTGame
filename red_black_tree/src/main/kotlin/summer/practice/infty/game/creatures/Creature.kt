package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player
import summer.practice.infty.game.getHealthDamageOfElemnts
import summer.practice.infty.game.getRatioFromElemets
import kotlin.math.max

interface Creature {
    val element: Element
    var health: Int
    val damage: Int
    var in_battle: Boolean
    val description: String
    val win_description: String

    fun attack(player: Player){
        player.health -= max(0, (damage * (if(getRatioFromElemets(element, player.getArmorElement()) == 0.0) 1.0
                                              else 1.5) - player.getArmorAbsorption()).toInt())
        health -= (10 * player.getArmorAbsorption() * getHealthDamageOfElemnts(element, player.getArmorElement())).toInt()
    }

    fun getDamageRatio(magic: Boolean = false): Double

    fun getActions(player: Player): ArrayList<Action>
}
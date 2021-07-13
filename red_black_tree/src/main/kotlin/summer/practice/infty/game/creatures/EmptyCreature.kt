package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

class EmptyCreature: Creature{
    override val element: Element = Element.NONE
    override var health: Int = 0
    override val damage: Int = 0
    override var in_battle: Boolean = false
    override val description: String = ""
    override val win_description: String = ""

    override fun attack(player: Player){}

    override fun getDamageRatio(magic: Boolean): Double = 0.0

    override fun getActions(player: Player): ArrayList<Action> = ArrayList()
}
package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

class Trader: Creature{
    override val element: Element = Element.NONE
    override var health: Int = 1
        set(value){field = 1}
    override val damage: Int = 0
    override var in_battle: Boolean = false
        set(value) {field = false}

    override fun Attack(player: Player) {}

    override fun getDamageRatio(magic: Boolean): Double = 0.0

    override fun getActions(): ArrayList<Action> {
        TODO("Not yet implemented")
    }

    fun buy(index: Int, player: Player){
        TODO("Not yet implemented")
    }
}
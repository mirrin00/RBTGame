package summer.practice.infty.game.creatures

import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

class Trader: Creature{
    override val element: Element = Element.NONE
    override val health: Int = 1000
    override val damage: Int = 0
    override var in_battle: Boolean = false
        set(value) {field = false}

    override fun Attack(player: Player) {}

    override fun getActions(): ArrayList<String> {
        TODO("Not yet implemented")
    }
}
package summer.practice.infty.game.creatures

import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

class Bandit(override val element: Element,
             override var health: Int,
             override val damage: Int): Creature{
    override var in_battle: Boolean = false

    override fun Attack(player: Player) {
        TODO("Not yet implemented")
    }

    override fun getActions(): ArrayList<String> {
        TODO("Not yet implemented")
    }
}
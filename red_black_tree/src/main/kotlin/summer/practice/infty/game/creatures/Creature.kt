package summer.practice.infty.game.creatures

import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

interface Creature {
    val element: Element
    val health: Int
    val damage: Int
    var in_battle: Boolean

    fun Attack(player: Player)

    fun getActions(): ArrayList<String>
}
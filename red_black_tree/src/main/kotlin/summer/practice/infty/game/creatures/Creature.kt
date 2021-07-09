package summer.practice.infty.game.creatures

import summer.practice.infty.game.Element

interface Creature {
    val element: Element
    val health: Int
    val damage: Int

}
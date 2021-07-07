package summer.practice.infty.game.generators

import summer.practice.infty.game.Creature
import summer.practice.infty.game.Element

internal interface CreatureFabric{
    fun generateCreature(element: Element): Creature
}
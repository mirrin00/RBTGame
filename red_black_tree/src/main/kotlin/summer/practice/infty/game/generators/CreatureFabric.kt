package summer.practice.infty.game.generators

import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.Element

internal interface CreatureFabric{
    fun generateCreature(deep_level: Int, element: Element): Creature
}
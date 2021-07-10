package summer.practice.infty.game.generators

import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.Element
import summer.practice.infty.game.creatures.EmptyCreature

internal class GameCreatureFabric: CreatureFabric{
    override fun generateCreature(deep_level: Int, element: Element): Creature = EmptyCreature() // TODO("Not yet implemented")
}
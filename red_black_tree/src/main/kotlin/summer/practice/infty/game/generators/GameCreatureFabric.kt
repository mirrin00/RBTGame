package summer.practice.infty.game.generators

import summer.practice.infty.game.Element
import summer.practice.infty.game.creatures.*
import kotlin.random.Random

internal class GameCreatureFabric: CreatureFabric{
    private fun generateHealth(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(0.75, 1.25)).toInt()

    private fun generateDamage(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(1.0, 1.5)).toInt()

    private fun generatePayCost(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(2.0, 5.0)).toInt()

    override fun generateCreature(depth_level: Int, element: Element): Creature = when((0..4).random()){
        0 -> Trader(Generator.generateItem(depth_level),
                    Generator.generateItem(depth_level),
                    Generator.generateItem(depth_level))
        1 -> Dragon(element,
                    generateHealth(depth_level),
                    generateDamage(depth_level))
        2 -> Golem(element,
                   generateHealth(depth_level),
                   generateDamage(depth_level))
        3 -> Bandit(element,
                    generateHealth(depth_level),
                    generateDamage(depth_level),
                    generatePayCost(depth_level))
        else -> EmptyCreature()
    }
}
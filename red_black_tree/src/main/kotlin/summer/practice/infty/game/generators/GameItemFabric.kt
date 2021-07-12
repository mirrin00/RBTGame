package summer.practice.infty.game.generators

import summer.practice.infty.game.items.Item
import kotlin.random.Random

internal class GameItemFabric: ItemFabric{

    private fun generateBasicValue(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(1.0, 2.0)).toInt()

    private fun generateAttributeValue(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(0.75, 1.25)).toInt()

    private fun generateCost(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(0.3, 1.2)).toInt()

    private fun generatePrice(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(1.2, 3.0)).toInt()

    override fun generateItem(depth_level: Int): Item = Item(Generator.generateElement(),
                                                            Generator.generateItemType(),
                                                            generateBasicValue(depth_level),
                                                            Generator.generateAttribute(),
                                                            generateAttributeValue(depth_level),
                                                            generateCost(depth_level),
                                                            generatePrice(depth_level), 0)
}
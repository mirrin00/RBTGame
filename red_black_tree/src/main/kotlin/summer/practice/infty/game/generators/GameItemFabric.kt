package summer.practice.infty.game.generators

import summer.practice.infty.game.items.Item
import kotlin.random.Random

internal class GameItemFabric: ItemFabric{

    private fun generateBasicValue(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * Random.nextDouble(1.0, 2.0)).toInt()

    private fun generateAttributeValue(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * Random.nextDouble(0.75, 1.25)).toInt()

    private fun generatePrice(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * Random.nextDouble(1.2, 3.0)).toInt()

    override fun generateItem(deep_level: Int): Item = Item(Generator.generateElement(),
                                                            Generator.generateItemType(),
                                                            generateBasicValue(deep_level),
                                                            Generator.generateAttribute(),
                                                            generateAttributeValue(deep_level),
                                                            generatePrice(deep_level), 0)
}
package summer.practice.infty.game.generators

import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType
import kotlin.random.Random

internal class GameItemFabric: ItemFabric{

    private fun generateBasicValue(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(1.0, 2.0)).toInt()

    private fun generateAttributeValue(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(0.75, 1.25)).toInt()

    private fun generateCost(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(0.3, 1.2)).toInt()

    private fun generatePrice(depth_level: Int) = (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(1.2, 3.0)).toInt()

    private fun generateHealthMagicIncrease(depth_level: Int, type: ItemType): Int = if(type != ItemType.AMULET) 0 else when((0..9).random()){
        1, 5, 7 -> (Generator.getRandomFromDepth(depth_level) * Random.nextDouble(2.5, 4.0)).toInt()
        0 -> -Random.nextInt(5, 20)
        else -> 0
    }

    override fun generateItem(depth_level: Int, type: ItemType): Item{
        val new_type = if(type == ItemType.EMPTY) Generator.generateItemType() else type
        return Item(Generator.generateElement(),
                    new_type,
                    generateBasicValue(depth_level),
                    Generator.generateAttribute(),
                    generateAttributeValue(depth_level),
                    generateCost(depth_level),
                    generateHealthMagicIncrease(depth_level, new_type),
                    generateHealthMagicIncrease(depth_level, new_type),
                    generatePrice(depth_level),0)
    }
}
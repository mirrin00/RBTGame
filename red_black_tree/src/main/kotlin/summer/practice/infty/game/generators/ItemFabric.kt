package summer.practice.infty.game.generators

import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType

internal interface ItemFabric{
    fun generateItem(depth_level: Int, type: ItemType = ItemType.EMPTY): Item
}
package summer.practice.infty.game.generators

import summer.practice.infty.game.items.Item

internal interface ItemFabric{
    fun generateItem(depth_level: Int): Item
}
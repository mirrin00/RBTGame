package summer.practice.infty.game.items

import summer.practice.infty.game.Attributes
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player
import kotlin.math.min

class Item(val element: Element,
           val type: ItemType,
           val basic_value: Int,
           val attr: Attributes,
           attr_val: Int,
           val cost: Int,
           val price: Int,
           var inv_number: Int){

    val attr_value: Int = if(attr == Attributes.DEXTERITY) min(80, attr_val)
                          else attr_val

    val tip: String = when(type){
        ItemType.WEAPON -> "Sword\n" + getDescription()
        ItemType.ARMOR -> "Armor\n" + getDescription()
        ItemType.MAGIC -> "Magic scroll\n" + getDescription()
        ItemType.AMULET -> "Mysterious amulet\nElement: $element\n$attr: $attr_value"
        ItemType.HEALTH_POTION -> "Health potion\nHealth restore: $basic_value\nPrice: $price"
        ItemType.MAGIC_POTION -> "Magic potion\nMagic restore: $basic_value\nPrice: $price"
        ItemType.OTHER -> "Relic\nPrice: $price"
        ItemType.EMPTY -> ""
    }

    private fun getDescription() = "Element: $element\nValue: $basic_value\n$attr: $attr_value\n" +
            (if(type == ItemType.MAGIC) "Cost: $cost\n" else "") + "Price: $price"

    fun use(player: Player){
        when(type){
            ItemType.WEAPON, ItemType.MAGIC,
            ItemType.ARMOR, ItemType.AMULET -> player.setActiveItem(this)
            ItemType.HEALTH_POTION -> {
                player.health += basic_value
                player.removeInventoryItem(inv_number)
            }
            ItemType.MAGIC_POTION -> {
                player.magic += basic_value
                player.removeInventoryItem(inv_number)
            }
            else -> {}
        }
    }
}

fun generateEmptyItem() = Item(Element.NONE, ItemType.EMPTY, 1, Attributes.NONE, 0,0, 0,0)
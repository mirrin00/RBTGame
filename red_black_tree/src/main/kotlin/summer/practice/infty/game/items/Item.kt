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
           val health_increase: Int,
           val magic_increase: Int,
           val price: Int,
           var inv_number: Int){

    val attr_value: Int = if(attr == Attributes.DEXTERITY) min(80, attr_val)
                          else attr_val

    val string_type: String = when(type){
        ItemType.WEAPON -> "Weapon"
        ItemType.ARMOR -> "Armor"
        ItemType.MAGIC -> "Magic scroll"
        ItemType.AMULET -> "Mysterious amulet"
        ItemType.HEALTH_POTION -> "Health potion"
        ItemType.MAGIC_POTION -> "Magic potion"
        ItemType.OTHER -> "Relic"
        ItemType.EMPTY -> ""
    }
    val tip: String = string_type + "\n" + when(type){
        ItemType.WEAPON -> getDescription()
        ItemType.ARMOR -> getDescription()
        ItemType.MAGIC -> getDescription()
        ItemType.AMULET -> "Element: $element\n$attr: $attr_value" +
                        (if(health_increase != 0) "\nMax health: +$health_increase" else "") +
                        (if(magic_increase != 0) "\nMax magic: +$magic_increase" else "")
        ItemType.HEALTH_POTION -> "Health restore: $basic_value\nPrice: $price"
        ItemType.MAGIC_POTION -> "Magic restore: $basic_value\nPrice: $price"
        ItemType.OTHER -> "Price: $price"
        ItemType.EMPTY -> ""
    }

    private fun getDescription() = "Element: $element\nValue: $basic_value\n" +
            (if(attr != Attributes.NONE) "$attr: $attr_value\n" else "") +
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

fun generateEmptyItem() = Item(Element.NONE, ItemType.EMPTY, 1, Attributes.NONE, 0,0, 0,0, 0,0)
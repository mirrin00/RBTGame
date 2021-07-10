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
           val price: Int,
           var inv_number: Int){

    val attr_value: Int
    init{
        attr_value = if(attr == Attributes.DEXTERITY) min(80, attr_val)
                     else attr_val
    }

    fun use(player: Player){

    }
}

fun generateEmptyItem() = Item(Element.NONE, ItemType.EMPTY, 0, Attributes.NONE, 0,0, 0)
package summer.practice.infty.game

import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType
import summer.practice.infty.game.items.generateEmptyItem
import kotlin.math.min
import kotlin.random.Random

private const val WEAPON_INDEX = 0
private const val ARMOR_INDEX = 1
private const val MAGIC_INDEX = 2
private const val AMULET_INDEX = 3

class Player{
    var coins: Int = 0
        set(value) {
            field = if(value < 0) 0
                    else value
        }
    var max_health = 100
        private set
    var health: Int = max_health
        set(value){
            field = when{
                value < 0 -> 0
                value > max_health -> max_health
                else -> value
            }
        }
    var max_magic = 100
        private set
    var magic: Int = max_magic
        set(value){
            field = when{
                value < 0 -> 0
                value > max_health -> max_health
                else -> value
            }
        }
    // Attributes
    // Basic value of attributes
    private val basic_perception: Int = Random.nextInt(1,10)
    private val basic_dextery: Int = 0
    private val basic_strength: Int = 0
    private val basic_intelligence: Int = 0
    // Completed value of attributes = basic + active_items.attr_values
    var perception: Int = basic_perception
            private set
    var dextery: Int = basic_dextery
        private set
    var strength: Int = basic_strength
        private set
    var intelligence: Int = basic_intelligence
        private set
    private val inventory = Array<Item>(INVENTORY_CAPACITY){ generateEmptyItem()}
    private val active_items = Array<Item>(4){ generateEmptyItem()}

    private fun recalculateAttributes(){
        perception = basic_perception
        dextery = basic_dextery
        strength = basic_strength
        intelligence = basic_intelligence
        for(item in active_items){
            when(item.attr){
                Attributes.DEXTERITY -> dextery += item.attr_value
                Attributes.PERCEPTION -> perception += item.attr_value
                Attributes.INTELLIGENCE -> intelligence += item.attr_value
                Attributes.STRENGTH -> strength += item.attr_value
                else -> {}
            }
        }
        dextery = min(90, dextery)
    }

    fun setActiveItem(item: Item){
        if(item.inv_number !in 0 until INVENTORY_CAPACITY) return
        val index = when(item.type){
            ItemType.WEAPON -> WEAPON_INDEX
            ItemType.ARMOR -> ARMOR_INDEX
            ItemType.AMULET -> AMULET_INDEX
            ItemType.MAGIC -> MAGIC_INDEX
            else -> return
        }
        active_items[index] = inventory[item.inv_number].also{
            inventory[item.inv_number] = active_items[index]
        }
        recalculateAttributes()
    }

    fun swapInventoryItems(item1: Item, item2: Item){
        swapInventoryItems(item1.inv_number, item2.inv_number)
    }

    fun swapInventoryItems(index1: Int, index2: Int){
        if(index1 !in 0 until INVENTORY_CAPACITY ||
           index2 !in 0 until INVENTORY_CAPACITY) return
        inventory[index1] = inventory[index2].also{
            inventory[index2] = inventory[index1]
        }
    }

    companion object{
        const val INVENTORY_CAPACITY = 6
    }
}
package summer.practice.infty.game

import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType
import summer.practice.infty.game.items.generateEmptyItem
import kotlin.math.min
import kotlin.random.Random

const val WEAPON_INDEX = 0
const val ARMOR_INDEX = 1
const val MAGIC_INDEX = 2
const val AMULET_INDEX = 3
private const val armor_coef = 0.1

class Player(val game: Game){
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
                value > field -> value
                else -> {
                    if((1..100).random() > dexterity) value
                    else field
                }
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
    var in_fight: Boolean = false
    var cur_room: Int = 0
    // Attributes
    // Basic value of attributes
    private var basic_perception: Int = Random.nextInt(1,10)
    private var basic_dexterity: Int = Random.nextInt(1,10)
    private var basic_strength: Int = Random.nextInt(1,10)
    private var basic_intelligence: Int = Random.nextInt(1,10)
    // Completed value of attributes = basic + active_items.attr_values
    var perception: Int = basic_perception
            private set
    var dexterity: Int = basic_dexterity
        private set
    var strength: Int = basic_strength
        private set
    var intelligence: Int = basic_intelligence
        private set
    private val inventory = Array<Item>(INVENTORY_CAPACITY){ val v = generateEmptyItem(); v.inv_number = it; v}
    private val active_items = Array<Item>(4){ val v = generateEmptyItem(); v.inv_number = it; v}

    private fun recalculateAttributes(){
        perception = basic_perception
        dexterity = basic_dexterity
        strength = basic_strength
        intelligence = basic_intelligence
        for(item in active_items){
            when(item.attr){
                Attributes.DEXTERITY -> dexterity += item.attr_value
                Attributes.PERCEPTION -> perception += item.attr_value
                Attributes.INTELLIGENCE -> intelligence += item.attr_value
                Attributes.STRENGTH -> strength += item.attr_value
                else -> {}
            }
        }
        dexterity = min(90, dexterity)
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
        inventory[item.inv_number].inv_number = active_items[index].inv_number
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
        inventory[index1].inv_number = index2
        inventory[index2].inv_number = index1
    }

    fun removeInventoryItem(item: Item){
        removeInventoryItem(item.inv_number)
    }

    fun removeInventoryItem(index: Int){
        if(index in 0 until INVENTORY_CAPACITY){
            inventory[index] = generateEmptyItem()
        }
    }

    fun removeActiveItem(item: Item){
        removeActiveItem(item.inv_number)
    }

    fun removeActiveItem(index: Int){
        if(index in active_items.indices){
            active_items[index] = generateEmptyItem()
        }
    }

    fun getItemsCountInInventory(): Int{
        var count = 0
        inventory.forEach { if(it.type != ItemType.EMPTY) count++ }
        return count
    }

    fun addItem(item: Item){
        for(i in inventory.indices){
            if(inventory[i].type == ItemType.EMPTY){
                item.inv_number = i
                inventory[i] = item
                return
            }
        }
    }

    fun soldInventoryItem(index: Int){
        if(index !in 0 until INVENTORY_CAPACITY) return
        coins += inventory[index].price
        inventory[index] = generateEmptyItem()
    }

    fun soldActiveItem(index: Int){
        if(index !in active_items.indices) return
        coins += active_items[index].price
        active_items[index] = generateEmptyItem()
    }

    fun Attack(creature: Creature, by_magic: Boolean = false){
        var att = if(by_magic) active_items[MAGIC_INDEX] else active_items[WEAPON_INDEX]
        var attr_ratio = 1.0 + 0.1 * (if(by_magic) intelligence else strength)
        if(by_magic){
            while(magic < att.cost && hasMagicPotion()){
                useMagicPotion()
            }
            if(magic < att.cost){
                att = active_items[WEAPON_INDEX]
                attr_ratio = 1.0 + 0.1 * strength
            }else{
                magic -= att.cost
            }
        }
        creature.health -= (att.basic_value * attr_ratio * creature.getDamageRatio(by_magic) +
                att.attr_value * getRatioFromElemets(att.element, creature.element)).toInt()
        health -= (att.attr_value * attr_ratio * getHealthDamageOfElemnts(att.element, creature.element)).toInt()
    }

    fun getArmorElement() = active_items[ARMOR_INDEX].element

    fun hasHealthPotion(): Boolean{
        for(item in inventory)
            if(item.type == ItemType.HEALTH_POTION) return true
        return false
    }

    fun hasMagicPotion(): Boolean{
        for(item in inventory)
            if(item.type == ItemType.MAGIC_POTION) return true
        return false
    }

    fun useHealthPotion(){
        var potion: Item? = null
        for(item in inventory)
            if(item.type == ItemType.HEALTH_POTION)
                potion = when{
                    potion == null -> item
                    potion.basic_value > item.basic_value -> item
                    else -> potion
                }
        potion?.use(this)
    }

    fun useMagicPotion(){
        var potion: Item? = null
        for(item in inventory)
            if(item.type == ItemType.MAGIC_POTION)
                potion = when{
                    potion == null -> item
                    potion.basic_value > item.basic_value -> item
                    else -> potion
                }
        potion?.use(this)
    }

    fun useItem(index: Int){
        if(index !in inventory.indices) return
        inventory[index].use(this)
    }

    fun reset(){
        for(i in active_items.indices) active_items[i] = generateEmptyItem()
        for(i in inventory.indices) inventory[i] = generateEmptyItem()
    }

    fun getArmorAbsorption() = active_items[ARMOR_INDEX].basic_value * armor_coef

    fun canUseMagic() = magic >= active_items[MAGIC_INDEX].cost

    fun getInventory() = inventory.copyOf()

    fun getActiveItems() = active_items.copyOf()

    fun getInventoryCapacity() = INVENTORY_CAPACITY

    fun getActiveCapacity() = active_items.size

    companion object{
        const val INVENTORY_CAPACITY = 6
    }
}
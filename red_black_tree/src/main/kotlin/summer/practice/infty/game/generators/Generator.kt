package summer.practice.infty.game.generators

import summer.practice.infty.game.Attributes
import summer.practice.infty.game.Element
import summer.practice.infty.game.items.ItemType
import kotlin.math.pow
import kotlin.random.Random

object Generator{
    private val room_fabric: RoomFabric = GameRoomFabric()
    private val creature_fabric: CreatureFabric = GameCreatureFabric()
    private val room_event_fabric: RoomEventFabric = UsualRoomEventFabric()
    private val item_fabric: ItemFabric = GameItemFabric()

    fun generateRoom(depth_level: Int) = room_fabric.generateRoom(depth_level)

    fun generateCreature(depth_level: Int, element: Element) = creature_fabric.generateCreature(depth_level, element)

    fun generateRoomEvent(depth_level: Int) = room_event_fabric.generateRoomEvent(depth_level)

    fun generateItem(depth_level: Int, type: ItemType = ItemType.EMPTY) = item_fabric.generateItem(depth_level, type)

    fun getRandomFromDepth(depth_level: Int) = getValueFromDeep(depth_level) * Random.nextDouble(0.5, 1.5)

    fun generateElement(red: Boolean? = null): Element{
        if(red == null){
            return when((0..6).random()){
                0 -> Element.USUAL
                1 -> Element.HELLISH
                2 -> Element.MARINE
                3 -> Element.FROSTY
                4 -> Element.SANDY
                5 -> Element.UNDERGROUND
                6 -> Element.HEAVENLY
                else -> Element.NONE
            }
        }else{
            return if(red) when((0..2).random()){
                0 -> Element.HELLISH
                1 -> Element.SANDY
                2 -> Element.UNDERGROUND
                else -> Element.HELLISH
            } else when((0..3).random()){
                0 -> Element.USUAL
                1 -> Element.MARINE
                2 -> Element.HEAVENLY
                3 -> Element.FROSTY
                else -> Element.USUAL
            }
        }
    }

    fun generateItemType(): ItemType = when((0 until ItemType.values().size - 1).random()){
        0 -> ItemType.ARMOR
        1 -> ItemType.WEAPON
        2 -> ItemType.MAGIC
        3 -> ItemType.AMULET
        4 -> ItemType.MAGIC_POTION
        5 -> ItemType.HEALTH_POTION
        6 -> ItemType.OTHER
        else -> ItemType.OTHER
    }

    fun generateAttribute(): Attributes = when((0 until Attributes.values().size).random()){
        0 -> Attributes.DEXTERITY
        1 -> Attributes.STRENGTH
        2 -> Attributes.INTELLIGENCE
        3 -> Attributes.PERCEPTION
        4 -> Attributes.NONE
        else -> Attributes.NONE
    }

    private fun getValueFromDeep(depth_level: Int): Double = when{
        depth_level <= 3 -> 2.0 * (depth_level + 1)
        else ->6.0 + 1.5 * (depth_level - 2).toDouble().pow(1.5)
    }
}
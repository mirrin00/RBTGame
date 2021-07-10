package summer.practice.infty.game.generators

import summer.practice.infty.game.Element
import summer.practice.infty.game.Game
import kotlin.random.Random

object Generator{
    private val room_fabric: RoomFabric = GameRoomFabric()
    private val creature_fabric: CreatureFabric = GameCreatureFabric()
    private val room_event_fabric: RoomEventFabric = UsualRoomEventFabric()

    fun generateRoom(deep_level: Int) = room_fabric.generateRoom(deep_level)

    fun generateCreature(deep_level: Int, element: Element) = creature_fabric.generateCreature(deep_level, element)

    fun generateRoomEvent(deep_level: Int) = room_event_fabric.generateRoomEvent(deep_level)

    fun getRandomFromDeep(deep_level: Int) = Game.getRatioFromDeep(deep_level) * Random.nextDouble(0.5, 2.5)
}
package summer.practice.infty.game.generators

import summer.practice.infty.game.Element

object Generator{
    private val room_fabric: RoomFabric = GameRoomFabric()
    private val creature_fabric: CreatureFabric = GameCreatureFabric()
    private val room_event_fabric: RoomEventFabric = UsualRoomEventFabric()

    fun generateRoom() = room_fabric.generateRoom()

    fun generateCreature(element: Element) = creature_fabric.generateCreature(element)

    fun generateRoomEvent() = room_event_fabric.generateRoomEvent()
}
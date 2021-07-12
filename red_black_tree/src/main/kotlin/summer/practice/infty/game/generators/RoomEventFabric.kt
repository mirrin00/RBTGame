package summer.practice.infty.game.generators

import summer.practice.infty.game.events.RoomEvent

internal interface RoomEventFabric{
    fun generateRoomEvent(depth_level: Int): RoomEvent
}
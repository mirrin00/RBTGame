package summer.practice.infty.game.rooms

import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.Element
import summer.practice.infty.game.events.RoomEvent

interface Room{
    val element: Element
    val creature: Creature
    val event: RoomEvent
    val type: RoomType
    val deep_level: Int

    fun regenerateRoom(red: Boolean? = null)
}

enum class RoomType{
    EMPTY_ROOM,
    USUAL_ROOM
}
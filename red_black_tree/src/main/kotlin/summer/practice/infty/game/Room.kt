package summer.practice.infty.game

import summer.practice.infty.game.events.RoomEvent

interface Room{
    val element: Element
    val creature: Creature
    val event: RoomEvent

    fun regenerateRoom(red: Boolean? = null)
}
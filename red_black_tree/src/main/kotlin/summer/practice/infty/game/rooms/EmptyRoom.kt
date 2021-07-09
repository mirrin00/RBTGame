package summer.practice.infty.game.rooms

import summer.practice.infty.game.Element
import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.creatures.EmptyCreature
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent

class EmptyRoom() : Room {
    override val element: Element = Element.NONE
    override val event: RoomEvent = EmptyEvent()
    override val creature: Creature = EmptyCreature()
    override val type: RoomType = RoomType.EMPTY_ROOM
    override val deep_level: Int = 0

    override fun regenerateRoom(red: Boolean?){}
}
package summer.practice.infty.game.rooms

import summer.practice.infty.game.Element
import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.creatures.EmptyCreature
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent

class EmptyRoom: Room {
    override val element: Element = Element.USUAL
    override val event: RoomEvent = EmptyEvent()
    override val creature: Creature = EmptyCreature()
    override val type: RoomType = RoomType.EMPTY_ROOM

    override fun regenerateRoom(red: Boolean?){}
}
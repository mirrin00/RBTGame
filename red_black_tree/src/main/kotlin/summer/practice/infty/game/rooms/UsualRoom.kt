package summer.practice.infty.game.rooms

import summer.practice.infty.game.creatures.Creature
import summer.practice.infty.game.Element
import summer.practice.infty.game.creatures.EmptyCreature
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent
import summer.practice.infty.game.generators.Generator
import java.lang.RuntimeException

class UsualRoom(override val deep_level: Int): Room {
    override var element: Element = Element.USUAL
        private set
    override var creature: Creature = EmptyCreature()
        private set
    override var event: RoomEvent = EmptyEvent()
        private set
    override val type: RoomType = RoomType.USUAL_ROOM

    init{
        regenerateRoom(null)
    }

    override fun regenerateRoom(red: Boolean?) {
        do {
            element = Generator.generateElement(red)
        }while(element == Element.NONE)
        creature = Generator.generateCreature(deep_level, element)
        event = Generator.generateRoomEvent(deep_level)
    }
}
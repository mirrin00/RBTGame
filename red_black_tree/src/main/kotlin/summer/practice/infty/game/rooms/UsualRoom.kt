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
        if(red == null){
            element = when((0..6).random()){
                0 -> Element.USUAL
                1 -> Element.HELLISH
                2 -> Element.MARINE
                3 -> Element.FROSTY
                4 -> Element.SANDY
                5 -> Element.UNDERGROUND
                6 -> Element.HEAVENLY
                else -> Element.USUAL
            }
        }else{
            element = if(red) when((0..2).random()){
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
        creature = Generator.generateCreature(deep_level, element)
        event = Generator.generateRoomEvent(deep_level)
    }
}
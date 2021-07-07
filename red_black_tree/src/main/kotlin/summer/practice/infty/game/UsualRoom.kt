package summer.practice.infty.game

import summer.practice.infty.game.events.RoomEvent
import summer.practice.infty.game.generators.Generator
import java.lang.RuntimeException

class UsualRoom: Room{
    override lateinit var element: Element
        private set
    override lateinit var creature: Creature
        private set
    override lateinit var event: RoomEvent
        private set

    init{
        regenerateRoom(null)
        if(!this::element.isInitialized)
            throw RuntimeException("UsualRoom: element is not initialized")
        if(!this::creature.isInitialized)
            throw RuntimeException("UsualRoom: creature is not initialized")
        if(!this::event.isInitialized)
            throw RuntimeException("UsualRoom: creature is not initialized")
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
        creature = Generator.generateCreature(element)
        event = Generator.generateRoomEvent()
    }
}
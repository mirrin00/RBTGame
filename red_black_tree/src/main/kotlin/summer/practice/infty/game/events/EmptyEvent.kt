package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class EmptyEvent: RoomEvent {
    override val k_count: Int = 0
    override val description: String = ""

    override fun actWithPlayer(player: Player) {}

    override fun canAct(player: Player): Boolean = true

    override fun getUseDescription(): String = getRequirements()

    override fun getRequirements(): String = ""
}
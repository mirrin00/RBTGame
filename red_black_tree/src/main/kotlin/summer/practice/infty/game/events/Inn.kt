package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class Inn(override val k_count: Int,
          private val cost: Int): RoomEvent {
    override val description: String = "" // TODO("Imagine description")

    override fun actWithPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun canAct(player: Player) = player.coins > cost

    override fun getRequirements(): String {
        TODO("Not yet implemented")
    }
}
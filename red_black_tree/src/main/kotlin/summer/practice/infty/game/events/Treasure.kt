package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class Treasure(override val k_count: Int,
               private val coins_count: Int): RoomEvent{
    override val description: String = "" // TODO("Imagine description")

    override fun actWithPlayer(player: Player) {
        player.changeCoins(coins_count)
    }

    override fun canAct(player: Player): Boolean = true

    override fun getRequirements(): String {
        TODO("Not yet implemented")
    }
}
package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class Treasure(override val k_count: Int,
               private val coins_count: Int): RoomEvent{
    override val description: String = "You find a treasure with $coins_count coins"

    override fun actWithPlayer(player: Player) {
        player.coins += coins_count
    }

    override fun getActionDescription(): String = "Take the treasure"

    override fun canAct(player: Player): Boolean = true

    override fun getUseDescription(): String = getRequirements()

    override fun getRequirements(): String = "You can take the treasure"
}
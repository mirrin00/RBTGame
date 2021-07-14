package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class Tracks(override val k_count: Int): RoomEvent{
    override val description: String = "You find tracks. They are left by " +
            "creatures from the following rooms. Explore the tracks to " +
            "learn more about these creatures"

    override fun actWithPlayer(player: Player){
        player.game.addNextCreatureDescription()
    }

    override fun getActionDescription(): String = "Explore the tracks"

    override fun canAct(player: Player): Boolean = true

    override fun getUseDescription(): String = getRequirements()

    override fun getRequirements(): String = "You can explore the tracks"
}
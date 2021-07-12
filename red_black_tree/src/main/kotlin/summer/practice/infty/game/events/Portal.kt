package summer.practice.infty.game.events

import summer.practice.infty.game.Player

class Portal(override val k_count: Int): RoomEvent {
    override val description: String = "There is a portal here. It can " +
            "move you to another room. Is it a good idea to use the portal? " +
            "Think twice."

    override fun actWithPlayer(player: Player){
        player.game.movePlayerOnDepth()
    }

    override fun canAct(player: Player): Boolean = true

    override fun getUseDescription(): String = getRequirements()

    override fun getRequirements(): String = "You can enter the portal"
}
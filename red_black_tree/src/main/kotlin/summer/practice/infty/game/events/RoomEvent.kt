package summer.practice.infty.game.events

import summer.practice.infty.game.Player

interface RoomEvent{
    val k_count: Int
    val description: String

    fun actWithPlayer(player: Player)

    fun getRequirements(): String

    fun getUseDescription(): String

    fun canAct(player: Player): Boolean

    fun isVisible(player_perception: Int) = player_perception > k_count
}
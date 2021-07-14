package summer.practice.infty.game.events

import summer.practice.infty.game.Player
import kotlin.math.max
import kotlin.math.min

class Inn(override val k_count: Int,
          private val cost: Int,
          private val max_regain: Int): RoomEvent {
    override val description: String = "There is an Inn for travelers. You " +
            "can rest and regain health and magics up to $max_regain points." +
            " The cost of rest is $cost coins"

    override fun actWithPlayer(player: Player) {
        player.health = max(max_regain, player.max_health)
        player.magic = max(max_regain, player.max_magic)
        player.coins -= cost
    }

    override fun getActionDescription(): String = "Rest in the Inn"

    override fun canAct(player: Player) = (player.coins > cost) &&
                                          (min(player.magic, player.health) < max_regain) &&
                                          (min(player.max_magic, player.max_health) >= max_regain)

    override fun getUseDescription(): String = "You can regain your health and magic for $cost coins"

    override fun getRequirements(): String = "You don't have a $cost coins or your health and magic is max for Inn"
}
package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionFight(private val magic: Boolean,
                  override val description: String,
                  override val tip: String = ""): Action{
    override fun act(game: Game) {
        game.fight(magic)
    }
}
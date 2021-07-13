package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionToFight(override val active: Boolean,
                    override val description: String,
                    override val tip: String = ""): Action{
    override fun act(game: Game) {
        game.toFight()
    }
}
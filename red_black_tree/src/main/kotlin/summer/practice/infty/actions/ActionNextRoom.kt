package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionNextRoom(private val left: Boolean,
                     override val description: String,
                     override val tip: String = ""): Action{
    override fun act(game: Game) {
        game.nextRoom(left)
    }
}
package summer.practice.infty.actions

import summer.practice.infty.game.Game

interface Action{
    val description: String
    val tip: String
    val active: Boolean

    fun act(game: Game)
}
package summer.practice.infty.actions

import summer.practice.infty.game.Game

interface Action{
    val description: String
    val tip: String

    fun act(game: Game)
}
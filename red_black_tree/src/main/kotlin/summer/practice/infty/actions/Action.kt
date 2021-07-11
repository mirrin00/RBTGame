package summer.practice.infty.actions

import summer.practice.infty.game.Game

interface Action{
    fun act(game: Game)
}
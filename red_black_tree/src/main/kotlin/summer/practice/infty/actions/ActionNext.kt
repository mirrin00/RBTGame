package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionNext: Action{
    override fun act(game: Game) {
        game.next()
    }
}
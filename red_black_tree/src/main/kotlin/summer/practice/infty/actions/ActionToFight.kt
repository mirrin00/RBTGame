package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionToFight: Action{
    override fun act(game: Game) {
        game.toFight()
    }
}
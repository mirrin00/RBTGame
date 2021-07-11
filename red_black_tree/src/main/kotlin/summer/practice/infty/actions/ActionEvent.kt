package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionEvent: Action{
    override fun act(game: Game) {
        game.actEvent()
    }
}
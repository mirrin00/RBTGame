package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionPay: Action{
    override fun act(game: Game) {
        game.pay()
    }
}
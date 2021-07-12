package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionPay(override val description: String,
                override val tip: String = ""): Action{
    override fun act(game: Game) {
        game.pay()
    }
}
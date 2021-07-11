package summer.practice.infty.actions

import summer.practice.infty.game.Game

class ActionBuy(private val index: Int): Action{
    override fun act(game: Game) {
        game.buy(index)
    }
}
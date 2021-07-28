package summer.practice.infty.controllers

import javafx.beans.binding.BooleanExpression
import summer.practice.infty.actions.Action
import summer.practice.infty.game.Difficulty
import summer.practice.infty.view.MyView
import tornadofx.Controller

class InterfaceController(gameWindow : MyView) : Controller(){
    private var picked : Int = -1
    val game = gameWindow.game
    fun swapItems(i : Int){
        if (picked < 0){
            picked = i
        }
        else{
            game.swapPlayerItems(picked, i)
            picked = -1
        }
    }
    fun resetPicked(){
        picked = -1
    }
    fun use(i : Int){
        game.useItem(i)
    }
    fun sell(i : Int, active : Boolean){
        game.sell(i, active)
    }
    fun drop(i : Int, active : Boolean){
        game.drop(i, active)
    }
    fun act(a : Action){
        a.act(game)
    }
    fun start(d : Int){
        game.start(when(d){
            DIFF_HARD -> Difficulty.HARD
            else -> Difficulty.NORMAL
        })
    }
    fun isEmptyItem(i : Int, active : Boolean) : Boolean {
        return game.isEmptyItem(i, active)
    }
    fun isUsableItem(i : Int) : Boolean {
        return game.isUsableItem(i)
    }
    fun inTrade() : Boolean{
        return game.isTrade()
    }

    companion object{
        const val DIFF_NORM = 0
        const val DIFF_HARD = 1
    }
}
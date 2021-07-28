package summer.practice.infty.controllers

import javafx.beans.binding.BooleanExpression
import summer.practice.infty.actions.Action
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
    fun start(){
        game.start()
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
}
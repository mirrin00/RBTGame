package summer.practice.infty.controllers

import summer.practice.infty.actions.Action
import summer.practice.infty.game.Game
import summer.practice.infty.view.MyView
import tornadofx.Controller

class InterfaceController(gameWindow : MyView) : Controller(){
    var picked : Int = -1
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
    fun sold(i : Int, active : Boolean){
        game.sold(i, active)
    }
    fun act(a : Action){
        a.act(game)
    }
    fun start(){
        game.start()
    }
}
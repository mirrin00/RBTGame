package summer.practice.infty.controllers

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import summer.practice.infty.view.MyView
import tornadofx.Controller
import javafx.scene.image.ImageView
import summer.practice.infty.ResourceLoader
import summer.practice.infty.actions.Action
import summer.practice.infty.drawable.DrawableTree
import summer.practice.infty.drawable.PartialTree
import summer.practice.infty.game.*
import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.generateEmptyItem
import summer.practice.infty.rbt.RedBlackTreeGame
import summer.practice.infty.view.FinalWindow


class ViewController(var gameWindow : MyView, val game : Game): Controller() {
    val final = FinalWindow()
    fun win(){
        val str: String = ("☆ Congratulations! You reached the leaf! ☆")
        final.changelabel(str)
        final.openWindow()
    }
    fun youDied(){
        val str: String = ("🕱 You died 🕱")
        final.changelabel(str)
        final.openWindow()
    }
    fun <T:Comparable<T>>updateTree(rbt : RedBlackTreeGame<T>){
        var drawableTree = DrawableTree<T>(rbt, draggableNodes = true)
        gameWindow.updateTree(drawableTree.createDrawnTree())
    }
    fun <T:Comparable<T>>updateLocalTree(rbt : RedBlackTreeGame<T>, key : T){
        var partialTree = PartialTree<T>(rbt, key)
        gameWindow.updateLocalTree(partialTree.getDrawnTree())
    }
    fun update(){
        changeLabel(gameWindow.health, game.getHealth())
        changeLabel(gameWindow.money, game.getCoins())
        changeLabel(gameWindow.magic, game.getMagic())

        changeLabel(gameWindow.attributes, game.getAttributes())
        changeLabel(gameWindow.textDescription, game.getDescription())

        gameWindow.actions = game.getActions()
        makeDisable()
        for (i in gameWindow.actions.indices){
            val b = when(i){
                0 -> gameWindow.act1
                1 -> gameWindow.act2
                2 -> gameWindow.act3
                3 -> gameWindow.act4
                else -> null
            } ?: continue
            val t = when(i){
                0 -> gameWindow.act1t
                1 -> gameWindow.act2t
                2 -> gameWindow.act3t
                3 -> gameWindow.act4t
                else -> null
            } ?: continue

            val a = gameWindow.actions.getOrNull(i) ?: continue
            changeButtonAbility(b, !a.active, a.description, t, a.tip)
        }

        val inventory = game.getInventory()
        for (i in inventory.indices){
            val b = when(i){
                0 -> gameWindow.inv0
                1 -> gameWindow.inv1
                2 -> gameWindow.inv2
                3 -> gameWindow.inv3
                4 -> gameWindow.inv4
                5 -> gameWindow.inv5
                else -> null
            } ?: continue
            val t = when(i){
                0 -> gameWindow.inv0t
                1 -> gameWindow.inv1t
                2 -> gameWindow.inv2t
                3 -> gameWindow.inv3t
                4 -> gameWindow.inv4t
                5 -> gameWindow.inv5t
                else -> null
            } ?: continue
            val item = inventory.getOrNull(i) ?: continue
            changeButton(b, ResourceLoader.getImage(item.type.name)!!, t, item.tip)
        }
        val activeItems = game.getActiveItems()
        for (i in activeItems.indices){
            val b = when(i){
                WEAPON_INDEX -> gameWindow.active1
                ARMOR_INDEX -> gameWindow.active2
                MAGIC_INDEX -> gameWindow.active3
                AMULET_INDEX -> gameWindow.active4
                else -> null
            } ?: continue
            val t = when(i){
                WEAPON_INDEX -> gameWindow.active1t
                ARMOR_INDEX -> gameWindow.active2t
                MAGIC_INDEX -> gameWindow.active3t
                AMULET_INDEX -> gameWindow.active4t
                else -> null
            } ?: continue
            val item = inventory.getOrNull(i) ?: continue
            changeButton(b, ResourceLoader.getImage(item.type.name)!!, t, item.tip)
        }
    }

    fun changeButton(b : Button, image : Image, t : Tooltip, tip : String){
        var i = ImageView()
        i.setImage(image)
        b.graphicProperty().setValue(i)
        t.text = tip
    }

    fun makeDisable() {
        gameWindow.act1.setDisable(true)
        gameWindow.act1.setVisible(false)
        gameWindow.act2.setDisable(true)
        gameWindow.act2.setVisible(false)
        gameWindow.act3.setDisable(true)
        gameWindow.act3.setVisible(false)
        gameWindow.act4.setDisable(true)
        gameWindow.act4.setVisible(false)
    }
    fun changeButtonAbility(b : Button, isDisabled : Boolean, text : String, t : Tooltip, tip : String){
        b.setVisible(true)
        b.setDisable(isDisabled)
        b.setText(text)
    }
    fun changeLabel(l : Label,  n : String){
        l.setText(n)
    }
}
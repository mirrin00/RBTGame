package summer.practice.infty.controllers

import javafx.scene.Group
import tornadofx.*
import java.util.*

import summer.practice.infty.drawable.DrawableTree
import summer.practice.infty.drawable.PartialTree
import summer.practice.infty.rbt.RedBlackTree
import summer.practice.infty.rbt.RedBlackTreeGame
import summer.practice.infty.game.rooms.Room
import summer.practice.infty.game.rooms.EmptyRoom
import summer.practice.infty.game.rooms.UsualRoom

class DrawingController: Controller(){
    var numberOfElements = 0
    var tree = RedBlackTreeGame<Int>()
    var drawableTree = DrawableTree<Int>(tree, draggableNodes = true)

    fun addNodeAction(){

        if(numberOfElements > 99){
            return
        }

        var key = (Math.random()*100).toInt()
        while(tree.find(key) != null){
            key = (Math.random()*100).toInt()
        }

        tree.insert(key, UsualRoom(1))
        numberOfElements += 1

        drawableTree.changeTree(DrawableTree(tree, draggableNodes = true))
    }

    fun removeNodeAction(key: Int){
        tree.delete(key)

        drawableTree.changeTree(DrawableTree(tree, draggableNodes = true))
    }

    fun drawTree(): Group{
        return drawableTree.createDrawnTree()
    }

    fun resetTree(){
        tree = RedBlackTreeGame()
        drawableTree = DrawableTree(tree, draggableNodes = true)
        numberOfElements = 0
    }

    fun drawCurrent(curKey: Int): Group{
        var partialTree = PartialTree<Int>(tree, curKey)

        return partialTree.getDrawnTree()
    }

}
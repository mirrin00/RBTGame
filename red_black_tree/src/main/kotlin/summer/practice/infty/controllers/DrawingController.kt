package summer.practice.infty.controllers

import javafx.scene.Group
import tornadofx.*
import java.util.*

import summer.practice.infty.drawable.DrawableTree
import summer.practice.infty.rbt.RedBlackTree

class DrawingController: Controller(){
    var numberOfElements = 0
    var tree = RedBlackTree<Int, Int>()
    var drawableTree = DrawableTree<Int, Int>(tree)

    fun addNodeAction(){

        if(numberOfElements > 100){
            return
        }

        var key = (Math.random()*100).toInt()
        while(tree.find(key) != null){
            key = (Math.random()*100).toInt()
        }

        tree.insert(key, (Math.random()*100).toInt())
        numberOfElements += 1

        drawableTree.changeTree(DrawableTree(tree))
    }

    fun removeNodeAction(key: Int){
        tree.delete(key)

        drawableTree.changeTree(DrawableTree(tree))
    }

    fun drawTree(): Group{
        return drawableTree.createDrawnTree()
    }

    fun resetTree(){
        tree = RedBlackTree()
        drawableTree = DrawableTree(tree)
        numberOfElements = 0
    }

}
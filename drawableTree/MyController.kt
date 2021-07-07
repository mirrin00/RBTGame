package summer.practice.infty.controllers
import javafx.scene.Group
import tornadofx.*

class MyController: Controller(){
    var numberOfElements = 0
    private val tree = RedBlackTree<Int, Int>()
    var drawableTree = DrawableTree<Int, Int>(tree)

    fun addNode(){
        tree.insert(numberOfElements, (Math.random()*100).toInt())
        numberOfElements++

        drawableTree = DrawableTree(tree)
    }

    fun removeNodeAction(){
        if(numberOfElements == 0){
            return
        }
        numberOfElements--
        tree.delete(numberOfElements)

        drawableTree = DrawableTree(tree)
    }

    fun drawTree(): Group{
        return drawableTree.createDrawnTree()
    }
}

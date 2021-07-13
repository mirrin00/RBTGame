package summer.practice.infty.drawable

import summer.practice.infty.game.rooms.*

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.shape.Line
import tornadofx.*
import kotlin.math.pow
import java.util.Queue
import java.util.LinkedList

import summer.practice.infty.rbt.RedBlackTree

class DrawableTree<T : Comparable<T>>(tree: RedBlackTree<T, Room> = RedBlackTree()
                                         , private val size: Double = 25.0
                                         , private val gap: Double = 100.0
                                         , private val draggableNodes: Boolean = false
                                            ) {
    private val iter = tree.iterator()
    private var height = tree.height
    private var root: DrawableNode<T>? = createNode(0.0, 0.0, height)

    private fun createNode(curX: Double, curY: Double, curHeight: Int, parent: Line? = null): DrawableNode<T>?{

        if(!iter.hasNext()){
            return null
        }

        val hasLeft = iter.hasLeftSon()
        val hasRight = iter.hasRightSon()

        val node = DrawableNode(curX, curY, iter, parentLink = parent, draggable = draggableNodes)

        val offset = ((((curHeight + 1) * (curHeight) + (size*2) * 2.0.pow(curHeight)) / 2) / 2 )

        if(hasLeft){
            node.leftLink = Line(curX, curY, curX - offset, curY + gap)
            node.leftLink!!.strokeWidth = size * 0.12
            node.leftNode = createNode(curX - offset, curY + gap, curHeight-1, node.leftLink)
        }

        if(hasRight){
            node.rightLink = Line(curX, curY, curX + offset, curY + gap)
            node.rightLink!!.strokeWidth = size * 0.12
            node.rightNode = createNode(curX + offset, curY + gap, curHeight-1, node.rightLink)
        }

        return node
    }

    //Returns group of nodes
    fun createDrawnTree(): Group{
        val treeGroup = Group()
        val queue: Queue<DrawableNode<T>> = LinkedList()

        if (root != null){
            queue.add(root)
        }

        var curNode: DrawableNode<T>
        while(!queue.isEmpty()) {
            curNode = queue.remove()

            treeGroup.apply {
                if(curNode.leftLink != null) {
                    add(curNode.leftLink!!)
                }

                if(curNode.rightLink != null) {
                    add(curNode.rightLink!!)
                }

                add(curNode.nodeShape)
            }

            if (curNode.leftNode != null) {
                queue.add(curNode.leftNode)
            }

            if (curNode.rightNode != null) {
                queue.add(curNode.rightNode)
            }

        }

        return treeGroup

    }

    private fun getNode(key: T): DrawableNode<T>?{

        val queue: Queue<DrawableNode<T>> = LinkedList()

        if(root == null){
            return null
        }

        queue.add(root)

        var curNode: DrawableNode<T>? = null
        while(!queue.isEmpty()){
            curNode = queue.remove()

            if(curNode.key == key){
                return curNode
            }

            if(curNode.leftNode != null && curNode.key > key){
                queue.add(curNode.leftNode)
            }else if(curNode.rightNode != null && curNode.key < key){
                queue.add(curNode.rightNode)
            }

        }

        return null
    }

    fun moveNode(key: T, changeX: Double, changeY: Double){
        val node = getNode(key) ?: return

        node.nodeShape.move(2.seconds, Point2D(node.nodeShape.translateX + changeX,
                                               node.nodeShape.translateY + changeY))

    }

    private fun getRoot(): DrawableNode<T>?{
        return root
    }

    fun changeTree(other: DrawableTree<T>){
        val nodesToChange = ArrayList<DrawableNode<T>>(0)
        val addedKeys = ArrayList<T>(0)

        val queue: Queue<DrawableNode<T>> = LinkedList()

        if(other.getRoot() == null){
            root = null
            return
        }

        queue.add(other.getRoot())

        var curNode: DrawableNode<T>?

        while(!queue.isEmpty()){
            curNode = queue.remove()

            if(getNode(curNode.key) == null){
                    addedKeys.add(curNode.key)
            }else {
                nodesToChange.add(getNode(curNode.key)!!)
            }


            if(curNode.leftNode != null){
                queue.add(curNode.leftNode)
            }

            if(curNode.rightNode != null){
                queue.add(curNode.rightNode)
            }
        }

        this.root = other.root
        this.height = other.height


        var offset = 0.0
        var node: DrawableNode<T>


        for(oldNode in nodesToChange){
            node = getNode(oldNode.key)!!

            val curX = node.nodeShape.layoutX
            val curY = node.nodeShape.layoutY


            node.nodeShape.layoutX = oldNode.nodeShape.layoutX
            node.nodeShape.layoutY = oldNode.nodeShape.layoutY

            timeline{

                keyframe(2.seconds){

                    keyvalue(node.nodeShape.layoutXProperty(), curX)
                    keyvalue(node.nodeShape.layoutYProperty(), curY)



                    if(node.parentLink != null){
                        keyvalue(node.parentLink!!.visibleProperty(), true)
                    }
                    if(node.leftLink != null){
                        keyvalue(node.leftLink!!.visibleProperty(), true)
                    }
                    if(node.rightLink != null){
                        keyvalue(node.rightLink!!.visibleProperty(), true)
                    }
                }

            }
        }

        for(key in addedKeys) {
            node = getNode(key)!!

            val curX = node.nodeShape.layoutX
            val curY = node.nodeShape.layoutY

            node.nodeShape.layoutX = offset
            node.nodeShape.layoutY = (height) * gap


            timeline{

                node.parentLink?.hide()
                node.leftLink?.hide()
                node.rightLink?.hide()

                keyframe(2.seconds) {

                    keyvalue(node.nodeShape.layoutXProperty(), curX)
                    keyvalue(node.nodeShape.layoutYProperty(), curY)

                    if(node.parentLink != null){
                        keyvalue(node.parentLink!!.visibleProperty(), true)
                    }
                    if(node.leftLink != null){
                        keyvalue(node.leftLink!!.visibleProperty(), true)
                    }
                    if(node.rightLink != null){
                        keyvalue(node.rightLink!!.visibleProperty(), true)
                    }
                }
            }
            offset += gap
        }
    }
}
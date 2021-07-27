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
                                         , val gap: Double = 100.0
                                         , private var startX: Double = 0.0
                                         , private var startY: Double = 0.0
                                         , private val cur_key: T? = null
                                         , private val draggableNodes: Boolean = false
                                            ) {
    private val iter = tree.iterator()
    private var height = tree.height
    private var root: DrawableNode<T>? = createNode(startX, startY, height)
    private val prevTreeGroup = Group()

    private fun createNode(curX: Double, curY: Double, curHeight: Int, parent: Line? = null): DrawableNode<T>?{

        if(!iter.hasNext()){
            return null
        }

        val hasLeft = iter.hasLeftSon()
        val hasRight = iter.hasRightSon()

        val node = DrawableNode(curX, curY, iter, parentLink = parent, draggable = draggableNodes, cur_key = cur_key)

        val offset = 2.0.pow(curHeight - 1) * (size + 6)/ 2

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

        treeGroup.apply { add(prevTreeGroup) }

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

            if(curNode.leftNode != null)
                queue.add(curNode.leftNode)
            if(curNode.rightNode != null)
                queue.add(curNode.rightNode)

        }

        return null
    }

    fun moveNode(key: T, changeX: Double, changeY: Double){
        val node = getNode(key) ?: return

        node.nodeShape.move(atime.seconds, Point2D(node.nodeShape.translateX + changeX,
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

            if(node.nodeShape.layoutX == curX && node.nodeShape.layoutY == curY)
                continue

            timeline{

                keyframe(atime.seconds){

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

            node.nodeShape.layoutX = startX + offset
            node.nodeShape.layoutY = startY + (height) * gap


            timeline{

                node.parentLink?.hide()
                node.leftLink?.hide()
                node.rightLink?.hide()

                keyframe(atime.seconds) {

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

    fun addSubTree(newRoot: T, other: DrawableTree<T>){
        val queue: Queue<DrawableNode<T>> = LinkedList()
        val otherRootY = other.root?.nodeShape?.layoutY ?: return
        val otherRootX = other.root?.nodeShape?.layoutX ?: return
        if(root != null) queue.add(root)
        while(!queue.isEmpty()){
            val curNode = queue.remove()
            if(curNode.leftNode != null){
                if(curNode?.leftNode?.key != newRoot){
                    queue.add(curNode.leftNode)
                }else{
                    curNode.leftLink?.endX = otherRootX
                    curNode.leftLink?.endY = otherRootY
                }
            }
            if(curNode.rightNode != null){
                if(curNode?.rightNode?.key != newRoot){
                    queue.add(curNode.rightNode)
                }else{
                    curNode.rightLink?.endX = otherRootX
                    curNode.rightLink?.endY = otherRootY
                }
            }
            prevTreeGroup.apply {
                if(curNode.leftLink != null) {
                    add(curNode.leftLink!!)
                }

                if(curNode.rightLink != null) {
                    add(curNode.rightLink!!)
                }

                add(curNode.nodeShape)
            }
        }
        root = getNode(newRoot)
        startX = other.startX
        startY = other.startY
        changeTree(other)
    }

    fun getNodePosition(key: T): Pair<Double, Double>{
        val node = getNode(key)
        return Pair(node?.nodeShape?.layoutX ?: 0.0, node?.nodeShape?.layoutY ?: 0.0)
    }

    fun getRootPosition() = Pair(root?.nodeShape?.layoutX ?: 0.0, root?.nodeShape?.layoutY ?: 0.0)

    companion object{
        const val atime = 2 // Action time in seconds for keyframes
    }
}
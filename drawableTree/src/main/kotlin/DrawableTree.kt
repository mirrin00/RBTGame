import javafx.scene.Group
import tornadofx.add
import kotlin.math.pow
import java.util.Queue
import java.util.LinkedList


class DrawableTree<T : Comparable<T>, V>(tree: RedBlackTree<T, V>){

    private val iter = tree.iterator()
    private val height = tree.height
    private val root: DrawableNode<T, V>? = createNode(0.0, 0.0, height)

    private fun createNode(curX: Double, curY: Double, curHeight: Int): DrawableNode<T, V>?{

        if(!iter.hasNext()){
            return null
        }

        val hasLeft = iter.hasLeftSon()
        val hasRight = iter.hasRightSon()

        val node = DrawableNode(curX, curY, iter)

        val offset = ((((curHeight + 1) * (curHeight) + 50.0 * 2.0.pow(curHeight)) / 2) / 2 )

        if(hasLeft){
            node.leftLink = DrawableLink(curX, curY, curX - offset, curY + 100)
            iter.next()
            node.leftNode = createNode(curX - offset, curY + 100, curHeight-1)
        }

        if(hasRight){
            node.rightLink = DrawableLink(curX, curY, curX + offset, curY + 100)
            iter.next()
            node.rightNode = createNode(curX + offset, curY + 100, curHeight-1)
        }

        return node
    }

    fun createDrawnTree(): Group{
        val treeGroup = Group()
        val queue: Queue<DrawableNode<T, V>> = LinkedList()

        if (root != null){
            queue.add(root)
        }

        var curNode: DrawableNode<T, V>
        while(!queue.isEmpty()) {
            curNode = queue.remove()

            treeGroup.apply {
                if(curNode.leftLink != null) {
                    add(curNode.leftLink!!.line)
                }

                if(curNode.rightLink != null) {
                    add(curNode.rightLink!!.line)
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

}
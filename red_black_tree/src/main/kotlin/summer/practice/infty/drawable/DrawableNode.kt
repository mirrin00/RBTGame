package summer.practice.infty.drawable

import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.TextBoundsType
import javafx.scene.text.Text
import tornadofx.*

import summer.practice.infty.rbt.RedBlackTreeIterator

class DrawableNode<T, V>(nodeX:Double = 0.0, nodeY:Double = 0.0, private val iter: RedBlackTreeIterator<T, V>,
                         var size: Double = 50.0, var parentLink: Line? = null, draggable: Boolean = false)
{
    val key = iter.getKey()
    var leftLink: Line? = null
    var rightLink: Line? = null
    var leftNode: DrawableNode<T, V>? = null
    var rightNode: DrawableNode<T, V>? = null

    var nodeShape = Group()
    private val mainShape = Circle()
    var textInNode = Text()

    private val color: Color = if (iter.isRed()) {Color.RED}
                else {Color.BLACK}

    init {
        mainShape.radius = size/2
        mainShape.fill = Color.WHITE

        //mainShape.fill = ImagePattern(Image("img.jpg"))

        mainShape.strokeWidth = 6.0
        mainShape.stroke = color

        var size = 35.0

        val keyValue = iter.getKey().toString()

        size /= keyValue.length


        textInNode.x = -10.0
        textInNode.y = (size - 5.0)/2
        textInNode.text = iter.getKey().toString()
        textInNode.font = Font(size)
        textInNode.fill = Color.rgb(0,0,0,0.25)
        textInNode.stroke = Color.rgb(0,0,0,1.0)
        textInNode.strokeWidth = 1.0/keyValue.length
        textInNode.boundsType = TextBoundsType.LOGICAL

        nodeShape.layoutX = nodeX
        nodeShape.layoutY = nodeY

        nodeShape.apply{

            add(mainShape)
            add(textInNode)

            /*Code for draging nodes*/

            if(draggable) {
                var startX = 0.0
                var startY = 0.0
                var initialTranslateX = translateX
                var initialTranslateY = translateY


                nodeShape.setOnMousePressed {
                    startX = it.x
                    startY = it.y
                    initialTranslateX = translateX
                    initialTranslateY = translateY

                    nodeShape.scene.cursor = Cursor.MOVE
                }


                nodeShape.setOnMouseReleased {
                    nodeShape.scene.cursor = Cursor.HAND
                }

                nodeShape.setOnMouseDragged {
                    moveNode(initialTranslateX + it.x - startX, initialTranslateY + it.y - startY)

                    it.consume()
                }
            }


        }

    }

    private fun moveNode(xChange: Double, yChange: Double){
        nodeShape.layoutX += xChange
        nodeShape.layoutY += yChange


        if(leftLink != null) {
            leftLink!!.startX += xChange
            leftLink!!.startY += yChange
        }

        if(rightLink != null) {
            rightLink!!.startX += xChange
            rightLink!!.startY += yChange
        }

        if(parentLink != null) {
            parentLink!!.endX += xChange
            parentLink!!.endY += yChange
        }
    }

}
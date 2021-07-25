package summer.practice.infty.drawable

import summer.practice.infty.game.rooms.*

import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.TextBoundsType
import javafx.scene.text.Text
import summer.practice.infty.ResourceLoader
import tornadofx.*

import summer.practice.infty.rbt.RedBlackTreeIterator

class DrawableNode<T>(nodeX:Double = 0.0, nodeY:Double = 0.0, private val iter: RedBlackTreeIterator<T, Room>,
                         var size: Double = 50.0, var parentLink: Line? = null, draggable: Boolean = false)
{
    //Values of node
    val key = iter.getKey()
    var leftNode: DrawableNode<T>? = null
    var rightNode: DrawableNode<T>? = null


    //Shapes of node
    var nodeShape = Group()
    var leftLink: Line? = null
    var rightLink: Line? = null
    private val mainShape = Circle()
    var textInNode = Text()

    //Color of node
    private val color: Color = when{
        iter.isSubRoot() -> Color.ORANGE
        iter.isRed() -> Color.RED
        else -> Color.BLACK
    }

    init {
        val keyValue = iter.getKey().toString()
        val curRoom = iter.next()


        val img = ResourceLoader.getImage(curRoom!!.element.name)

        mainShape.fill = if(img != null) {
            ImagePattern(img)
        }else{
            Color.WHITE
        }

        mainShape.radius = size/2
        mainShape.strokeWidth = 3.0
        mainShape.stroke = color

        var size = 35.0

        size /= keyValue.length


        textInNode.x = -10.0
        textInNode.y = (size - 5.0)/2
        textInNode.text = keyValue
        textInNode.font = Font(size)
        textInNode.fill = Color.rgb(255,255,255,1.0)
        textInNode.stroke = Color.rgb(0,0,0,1.0)
        textInNode.strokeWidth = 1.0/keyValue.length
        textInNode.boundsType = TextBoundsType.LOGICAL

        nodeShape.layoutX = nodeX
        nodeShape.layoutY = nodeY

        nodeShape.apply{

            add(mainShape)
            add(textInNode)

            /*Code for dragging nodes*/

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
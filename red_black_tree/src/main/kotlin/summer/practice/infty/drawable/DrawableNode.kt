package summer.practice.infty.drawable

import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Font
import javafx.scene.paint.ImagePattern
import javafx.scene.text.TextAlignment
import tornadofx.*

class DrawableNode<T, V>(var nodeX:Double = 0.0, var nodeY:Double = 0.0, private val iter: RedBlackTreeIterator<T, V>,
                         var size: Double = 50.0, var parentLink: DrawableLink? = null)
{
    var leftLink: DrawableLink? = null
    var rightLink: DrawableLink? = null
    var leftNode: DrawableNode<T, V>? = null
    var rightNode: DrawableNode<T, V>? = null

    var nodeShape = Group()
    private val mainShape = Circle()

    private val color: Color = if (iter.isRed()) {Color.RED}
                else {Color.BLACK}

    private val keyString = iter.getKey().toString()

    init {
        mainShape.radius = size/2
        mainShape.fill = Color.LIGHTBLUE
        //mainShape.fill = ImagePattern(Image("img.jpg"))
        mainShape.centerX = nodeX
        mainShape.centerY = nodeY
        mainShape.strokeWidth = 6.0
        mainShape.stroke = color

        nodeShape.apply{
            add(mainShape)

            text{
                x = nodeX - 10.0
                y = nodeY + 12.5
                textAlignment = TextAlignment.CENTER
                text = keyString
                font = Font(35.0)
                fill = Color.rgb(0,0,0,0.25)
                stroke = Color.rgb(0,0,0,1.0)
                strokeWidth = 1.0
            }
        }

    }

}

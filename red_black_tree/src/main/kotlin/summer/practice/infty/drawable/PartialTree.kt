package summer.practice.infty.drawable

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType
import summer.practice.infty.ResourceLoader

import summer.practice.infty.rbt.RedBlackTreeGame
import tornadofx.add
import tornadofx.circle

class PartialTree<T : Comparable<T>>(treeGame: RedBlackTreeGame<T>, curKey: T) {

    private val treeGroup = Group()

    init{

        val roomsInfo = treeGame.getRoomWithSons(curKey)
        val keys = treeGame.getLeftRightKeys(curKey)


        if(roomsInfo.first != null) {
            //creating curNode
            val curNode = Group()

            curNode.apply {
                layoutX = 0.0
                layoutY = 0.0


                circle {
                    radius = 25.0

                    val img = ResourceLoader.getImage(roomsInfo.first!!.element.name)

                    fill = if(img != null) {
                        ImagePattern(img)
                    }else{
                        Color.WHITE
                    }

                    strokeWidth = 3.0
                    stroke = Color.GREEN
                }

                add(getTextForKey(curKey))
            }

            //Creating leftNode
            if(roomsInfo.second != null){
                //Link to leftNode
                val leftLink = Line(0.0, 0.0, -50.0, 100.0)
                leftLink.strokeWidth = 3.0
                treeGroup.add(leftLink)

                val leftNode = Group()

                leftNode.apply {
                    layoutX = -50.0
                    layoutY = 100.0

                    circle {
                        radius = 25.0

                        val img = ResourceLoader.getImage(roomsInfo.second!!.element.name)
                        fill = if(img != null) {
                            ImagePattern(img)
                        }else{
                            Color.WHITE
                        }

                        strokeWidth = 3.0
                        stroke = Color.BLACK
                    }

                    if(keys.first != null) add(getTextForKey(keys.first!!))
                }
                treeGroup.add(leftNode)
            }


            //Creating rightNode
            if(roomsInfo.third != null){
                //Link to rightNode
                val rightLink = Line(0.0, 0.0, 50.0, 100.0)
                rightLink.strokeWidth = 3.0
                treeGroup.add(rightLink)

                val rightNode = Group()

                rightNode.apply {
                    layoutX = 50.0
                    layoutY = 100.0

                    circle {
                        radius = 25.0

                        val img = ResourceLoader.getImage(roomsInfo.third!!.element.name)
                        fill = if(img != null) {
                            ImagePattern(img)
                        }else{
                            Color.WHITE
                        }

                        strokeWidth = 3.0
                        stroke = Color.BLACK
                    }

                    if(keys.second != null) add(getTextForKey(keys.second!!))
                }
                treeGroup.add(rightNode)
            }

            treeGroup.add(curNode)
        }
    }

    fun getTextForKey(key: T): Text{
        val text = Text()
        val keyValue = key.toString()
        val size = 35.0/keyValue.length
        text.x = -10.0
        text.y = (size - 5.0)/2
        text.text = keyValue
        text.font = Font(size)
        text.fill = Color.rgb(255,255,255,1.0)
        text.stroke = Color.rgb(0,0,0,1.0)
        text.strokeWidth = 1.0/keyValue.length
        text.boundsType = TextBoundsType.LOGICAL
        return text
    }

    fun getDrawnTree(): Group{
        return treeGroup
    }
}

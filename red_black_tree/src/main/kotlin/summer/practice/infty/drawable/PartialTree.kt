package summer.practice.infty.drawable

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.TextBoundsType
import summer.practice.infty.ResourceLoader

import summer.practice.infty.rbt.RedBlackTreeGame
import tornadofx.add
import tornadofx.circle

class PartialTree<T : Comparable<T>>(treeGame: RedBlackTreeGame<T>, curKey: T) {

    private val treeGroup = Group()

    init{

        val roomsInfo = treeGame.getRoomWithSons(curKey)


        if(roomsInfo.first != null) {

            //Creating curNode
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
                }
                treeGroup.add(rightNode)
            }

            treeGroup.add(curNode)
        }
    }


    //Return group that can be added to plane
    fun getDrawnTree(): Group{
        return treeGroup
    }
}
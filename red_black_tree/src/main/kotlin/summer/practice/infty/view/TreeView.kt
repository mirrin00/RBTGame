package summer.practice.infty.view

import javafx.scene.Group
import javafx.scene.layout.StackPane
import summer.practice.infty.controllers.MyController
import tornadofx.*


class TreeView: View() {

    override val root = vbox {
        val myController = MyController()

        var mainPane = StackPane()
        var treeGroup: Group

        val scroll = scrollpane {

            setMaxSize(600.0, 750.0)
            setMinSize(600.0, 750.0)

            hvalue = 0.5
            vvalue = 0.5

            mainPane = stackpane {

                setOnScroll {
                    val movement = it.deltaY
                    var zoomFactor = 1.05

                    if (movement < 0){
                        zoomFactor = 0.90
                    }

                    mainPane.scaleX = mainPane.scaleX * zoomFactor
                    mainPane.scaleY = mainPane.scaleY * zoomFactor

                    it.consume()
                }

                treeGroup = Group()

                setMinSize(1000000.0, 1000000.0) //TODO: add resize
                isPannable = true
            }

        }

        button {
            label("Draw+")
            action {
                myController.addNode()
                treeGroup = myController.drawTree()

                mainPane.clear()
                mainPane.add(treeGroup)

                scroll.hvalue = 0.5
                scroll.vvalue = 0.5
            }
        }

        button {
            label("Draw-")
            action {
                myController.removeNodeAction()
                treeGroup = myController.drawTree()

                mainPane.clear()
                mainPane.add(treeGroup)

                scroll.hvalue = 0.5
                scroll.vvalue = 0.5
            }
        }


    }
}






package summer.practice.infty.view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Group
import javafx.scene.control.ScrollPane
import javafx.scene.layout.StackPane
import tornadofx.*

import summer.practice.infty.controllers.DrawingController


class TreeView: View() {

    override val root = vbox {
        val myController = DrawingController()
        val input = SimpleStringProperty()

        var mainPane = StackPane()
        var treeGroup: Group

        val scroll = scrollpane {

            setMinSize(600.0, 750.0)

            hvalue = 0.5
            vvalue = 0.5

            mainPane = stackpane {

                treeGroup = Group()

                setMinSize(1000000.0, 1000000.0) //TODO: add resize
                isPannable = true
                hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER


                setOnScroll {

                    //limit x10 zoomIn
                    if(mainPane.scaleX < 10 && it.deltaY > 0 || it.deltaY < 0) {
                        val movement = it.deltaY
                        var zoomFactor = 1.05

                        if (movement < 0) {
                            zoomFactor = 0.90
                        }

                        mainPane.scaleX = mainPane.scaleX * zoomFactor
                        mainPane.scaleY = mainPane.scaleY * zoomFactor
                    }

                    it.consume()
                }
            }

        }

        textfield(input)

        button {
            label("Add random node")
            action {

                myController.addNodeAction()
                treeGroup = myController.drawTree()

                mainPane.clear()
                mainPane.add(treeGroup)

                scroll.hvalue = 0.5
                scroll.vvalue = 0.5
            }
        }

        button {
            label("Remove node")
            action {
                try{
                    myController.removeNodeAction(input.value.toInt())
                    input.value = ""
                    treeGroup = myController.drawTree()

                    mainPane.clear()
                    mainPane.add(treeGroup)

                    scroll.hvalue = 0.5
                    scroll.vvalue = 0.5
                }
                catch (e: Exception){

                }
            }
        }

        button {
            label("Reset tree")
            action {
                myController.resetTree()

                treeGroup = myController.drawTree()

                mainPane.clear()
                mainPane.add(treeGroup)

                scroll.hvalue = 0.5
                scroll.vvalue = 0.5
            }
        }

        button {
            label("Draw current")
            action {
                treeGroup = myController.drawCurrent(input.value.toInt())
                input.value = ""

                mainPane.clear()
                mainPane.add(treeGroup)

            }
        }

    }
}






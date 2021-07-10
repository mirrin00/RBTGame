package summer.practice.infty.view

import javafx.scene.Group
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import summer.practice.infty.controllers.DrawingController
import tornadofx.*

class MyMap : View(){
    override val root : VBox by fxml("/MyMap.fxml")
    val myController = DrawingController()
    val mainPane : StackPane by fxid("stackk")
    val textinput : TextField by fxid("text")
    var treeGroup = Group()
    fun plusNode2( ) {
        myController.addNodeAction()
        treeGroup = myController.drawTree()
        mainPane.clear()
        mainPane.add(treeGroup)

    }
    fun minusNode2() {
        try{
            myController.removeNodeAction(textinput.getText(0,textinput.length).toInt())
            treeGroup = myController.drawTree()
            mainPane.clear()
            mainPane.add(treeGroup)
        }
        catch (e: Exception){

        }
    }
    fun resetTree2(){
        myController.resetTree()
        treeGroup = myController.drawTree()
        mainPane.clear()
        mainPane.add(treeGroup)
    }

}
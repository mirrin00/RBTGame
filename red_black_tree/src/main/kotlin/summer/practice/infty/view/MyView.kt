package summer.practice.infty.view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Group
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.control.ScrollPane
import javafx.scene.control.Button
import summer.practice.infty.controllers.DrawingController
import tornadofx.*


class MyView: View() {
    override val root : BorderPane by fxml("/MyInterface.fxml")
    val RBTmap = MyMap()
    val butt : Button by fxid("act3")
    fun plusNode() {
        RBTmap.plusNode2()
    }
    fun minusNode() {
        RBTmap.minusNode2()
    }
    fun resetTree(){
        RBTmap.resetTree2()
    }
    fun OpenTheMap(){
        RBTmap.openWindow()
    }

    fun disableAct(){
        if(!butt.isDisable()){
            butt.setDisable(true)
            butt.setVisible(false)
        }
        else{
            butt.setDisable(false)
            butt.setVisible(true)
        }
    }
}

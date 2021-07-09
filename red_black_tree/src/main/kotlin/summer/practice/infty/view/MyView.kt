package summer.practice.infty.view

import summer.practice.infty.controllers.MyController
import javafx.scene.Group
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import tornadofx.*


class MyView: View() {
    override val root : BorderPane by fxml("/MyInterface.fxml")
    val myController = MyController()

    val mainPane : StackPane by fxid("stac")
    var treeGroup = Group()

    fun plusNode() {
        myController.addNode()
        treeGroup = myController.drawTree()

        mainPane.clear()
        mainPane.add(treeGroup)
    }
    fun minusNode() {
        myController.removeNodeAction()
        treeGroup = myController.drawTree()

        mainPane.clear()
        mainPane.add(treeGroup)
    }
}

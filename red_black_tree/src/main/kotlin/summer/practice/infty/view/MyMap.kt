package summer.practice.infty.view

//import summer.practice.infty.controllers.MyController
import javafx.scene.Group
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import summer.practice.infty.controllers.DrawingController
import tornadofx.*

class MyMap() : View("Map"){

    override val root : VBox by fxml("/MyMap.fxml")
    val myController = DrawingController()
    val mainPane : StackPane by fxid("stackk")
    val scroll : ScrollPane by fxid("scroll")
    //val textinput : TextField by fxid("text")
    var treeGroup = Group()
    init{
        scroll.setMinSize(600.0, 750.0)
        mainPane.setMinSize(1000000.0, 1000000.0)
        mainPane.setOnScroll {
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
    fun drawMap(imageTree : Group){
        mainPane.clear()
        mainPane.add(imageTree)
    }

    /*
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
    fun drawcurrent2() : Group{
        treeGroup = myController.drawCurrent(textinput.getText(0,textinput.length).toInt())
        return treeGroup
        /
    }*/
}
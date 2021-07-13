package summer.practice.infty.view


import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

class FinalWindow() : View("End of the Game"){
    override val root : VBox by fxml("/FinalWindow.fxml")
    val Finalinfo : Label by fxid("Finalinfo")

    fun End(){
        close()
    }
    fun changelabel(str :String){
        Finalinfo.setText(str)
    }


}
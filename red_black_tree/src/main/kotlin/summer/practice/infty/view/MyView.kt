package summer.practice.infty.view

import javafx.scene.Group
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import summer.practice.infty.ResourceLoader
import summer.practice.infty.actions.Action
import summer.practice.infty.controllers.InterfaceController
import summer.practice.infty.controllers.ViewController
import summer.practice.infty.game.Game
import tornadofx.*


class MyView: View("Red Black Tree Game") {
    override val root : BorderPane by fxml("/MyInterface.fxml")
    //var treeGroup = Group()
    val RBTmap = MyMap()
    val game = Game()
    val vcontrol = ViewController<Int>(this)
    val icontrol = InterfaceController(this)
    val mainPane : StackPane by fxid("stackpane")
    var actions = ArrayList<Action>()
    var menu : ContextMenu? = null
        set(value) {
            field?.hide()
            field = value
        }

    //Button's:
        //actions 1-4
    val act1 : Button by fxid("action1")
    val act2 : Button by fxid("action2")
    val act3 : Button by fxid("action3")
    val act4 : Button by fxid("action4")
        //inventory:
    val active1 : Button by fxid("active1")
    val active2 : Button by fxid("active2")
    val active3 : Button by fxid("active3")
    val active4 : Button by fxid("active4")
    val inv0 : Button by fxid("inv0")
    val inv1 : Button by fxid("inv1")
    val inv2 : Button by fxid("inv2")
    val inv3 : Button by fxid("inv3")
    val inv4 : Button by fxid("inv4")
    val inv5 : Button by fxid("inv5")
    //Toolip
    val act1t : Tooltip by fxid("action1t")
    val act2t : Tooltip by fxid("action2t")
    val act3t : Tooltip by fxid("action3t")
    val act4t : Tooltip by fxid("action4t")

    val active1t : Tooltip by fxid("active1t")
    val active2t : Tooltip by fxid("active2t")
    val active3t : Tooltip by fxid("active3t")
    val active4t : Tooltip by fxid("active4t")
    val inv0t : Tooltip by fxid("inv0t")
    val inv1t : Tooltip by fxid("inv1t")
    val inv2t : Tooltip by fxid("inv2t")
    val inv3t : Tooltip by fxid("inv3t")
    val inv4t : Tooltip by fxid("inv4t")
    val inv5t : Tooltip by fxid("inv5t")
        //label:
    val health : Label by fxid("health")
    val healtht: Tooltip by fxid("healtht")
    val money : Label by fxid("money")
    val moneyt: Tooltip by fxid("moneyt")
    val magic : Label by fxid("magic")
    val magict: Tooltip by fxid("magict")
    val attributes : Label by fxid("attributes")
    val textDescription : Label by fxid("textDescription")

        //ImageView
    val imagePlayer : ImageView by fxid("ImagePlayer")
    init{
        val image = ResourceLoader.getImage("PLAYER")
        if(image != null) imagePlayer.setImage(image)
        game.view = vcontrol
        mainPane.scaleX = 1.8
        mainPane.scaleY = 1.8
        currentStage?.setOnCloseRequest { RBTmap.close() }
    }
    fun startGameNormal(){
        icontrol.start(InterfaceController.DIFF_NORM)
    }
    fun startGameHard(){
        icontrol.start(InterfaceController.DIFF_HARD)
    }

    fun pickedAction1(){
        val a = actions.getOrNull(0) ?: return
        icontrol.act(a)
    }
    fun pickedAction2(){
        val a = actions.getOrNull(1) ?: return
        icontrol.act(a)
    }
    fun pickedAction3(){
        val a = actions.getOrNull(2) ?: return
        icontrol.act(a)
    }
    fun pickedAction4(){
        val a = actions.getOrNull(3) ?: return
        icontrol.act(a)
    }

    fun pickedActive1(i : MouseEvent){
        icontrol.resetPicked()
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(0, true) ?: return
            menu?.show(act1, i.screenX, i.screenY)
        }
    }
    fun pickedActive2(i : MouseEvent){
        icontrol.resetPicked()
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(1, true) ?: return
            menu?.show(act2, i.screenX, i.screenY)
        }
    }
    fun pickedActive3(i : MouseEvent){
        icontrol.resetPicked()
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(2, true) ?: return
            menu?.show(act3, i.screenX, i.screenY)
        }
    }
    fun pickedActive4(i : MouseEvent){
        icontrol.resetPicked()
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(3, true) ?: return
            menu?.show(act4, i.screenX, i.screenY)
        }
    }
    fun pickedInv0(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(0)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(0, false) ?: return
            menu?.show(inv0, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(0)
            icontrol.resetPicked()
        }
    }
    fun pickedInv1(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(1)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(1, false) ?: return
            menu?.show(inv1, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(1)
            icontrol.resetPicked()
        }
    }
    fun pickedInv2(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(2)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(2, false) ?: return
            menu?.show(inv2, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(2)
            icontrol.resetPicked()
        }
    }
    fun pickedInv3(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(3)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(3, false) ?: return
            menu?.show(inv3, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(3)
            icontrol.resetPicked()
        }
    }
    fun pickedInv4(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(4)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(4, false) ?: return
            menu?.show(inv4, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(4)
            icontrol.resetPicked()
        }
    }
    fun pickedInv5(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(5)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            menu = getMenu(5, false) ?: return
            menu?.show(inv5, i.screenX, i.screenY)
            icontrol.resetPicked()
        }
        if(i.getButton() == MouseButton.MIDDLE) {
            icontrol.use(5)
            icontrol.resetPicked()
        }
    }
    fun pickedRules(){
        val rules = Alert(AlertType.INFORMATION)
        rules.isResizable = true
        rules.title = "Rules"
        rules.headerText = null
        val text = TextArea()
        text.text = "The main goal of the game is to reach the leaf " +
                "of the tree. Each room has an element. This is very important " +
                "because they react. So don't do anything rash. Also be careful" +
                " with the Hellish-Marine reaction and the Heavenly-Frosty " +
                "reaction, because you will take damage from your attack.\nIn " +
                "your inventory, you can swap items by clicking on the primary " +
                "mouse button. You can use items by clicking on the secondary " +
                "mouse button. Items such as weapons, armor, a magic scroll and " +
                "a mysterious amulet are placed in the active slot when used. " +
                "You can also drop items from your inventory by clicking on them " +
                "with the middle mouse button. If you are trading, this action " +
                "will sell the item.\nThe map button opens the current tree " +
                "map, which can change during your journey. Look there more " +
                "often, it will help you reach your goal.\nGood luck!"
        text.isWrapText = true
        text.isEditable = false
        rules.dialogPane.content = text
        rules.showAndWait()
    }


    fun OpenTheMap(){
        RBTmap.openWindow(owner = null)
    }
    fun updateTree(imageTree : Group){
        RBTmap.drawMap(imageTree)
    }
    fun updateLocalTree( imageTree: Group){
        mainPane.clear()
        mainPane.add(imageTree)
    }
    fun getMenu(i : Int, active : Boolean): ContextMenu?{
        return if(icontrol.isEmptyItem(i, active)) null
        else contextmenu {
            item("use") {
                isDisable = active || !icontrol.isUsableItem(i)
                setOnAction {
                    icontrol.use(i)
                }
            }
            item("sell"){
                isDisable = !icontrol.inTrade()
                setOnAction {
                    icontrol.sell(i, active)
                }
            }
            item("drop").action {
                icontrol.drop(i, active)
            }
            isAutoHide = true
        }
    }
}

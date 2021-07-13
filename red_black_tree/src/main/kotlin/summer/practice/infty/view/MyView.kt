package summer.practice.infty.view

import javafx.scene.Group
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import summer.practice.infty.controllers.InterfaceController
import tornadofx.*
import javafx.scene.input.MouseEvent
import summer.practice.infty.actions.Action


class MyView: View("Red Black Tree Game") {
    override val root : BorderPane by fxml("/MyInterface.fxml")
    var treeGroup = Group()
    val RBTmap = MyMap()
    val icontrol = InterfaceController(this)
    val mainPane : StackPane by fxid("stackpane")
    var actions = ArrayList<Action>()

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
    val money : Label by fxid("money")
    val magic : Label by fxid("magic")
    val attributes : Label by fxid("attributes")
    val textDescription : Label by fxid("textDescription")

        //ImageView
    val imagePlayer : ImageView by fxid("ImagePlayer")

    fun startGame(){
        icontrol.start()
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
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(0, true)
        }
    }
    fun pickedActive2(i : MouseEvent){
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(1, true)
        }
    }
    fun pickedActive3(i : MouseEvent){
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(2, true)
        }
    }
    fun pickedActive4(i : MouseEvent){
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(3, true)
        }
    }
    fun pickedInv0(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(0)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(0, false)
        }
    }
    fun pickedInv1(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(1)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(1, false)
        }
    }
    fun pickedInv2(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(2)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(2, false)
        }
    }
    fun pickedInv3(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(3)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(3, false)
        }
    }
    fun pickedInv4(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(4)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(4, false)
        }
    }
    fun pickedInv5(i : MouseEvent){
        if(i.getButton() == MouseButton.PRIMARY){
            icontrol.swapItems(5)
        }
        if(i.getButton() == MouseButton.SECONDARY) {
            icontrol.sold(5, false)
        }
    }

    fun OpenTheMap(){
        RBTmap.openWindow()
    }
    fun updateTree(imageTree : Group){
        RBTmap.drawMap(imageTree)
    }
    fun updateLocalTree( imageTree: Group){
        mainPane.clear()
        mainPane.add(imageTree)
    }

    fun plusNode() {
       // RBTmap.plusNode2()
    }
    fun minusNode() {
        //RBTmap.minusNode2()
    }
    fun resetTree(){
       // RBTmap.resetTree2()
    }
    fun drawcurrent(){
        /*treeGroup = RBTmap.drawcurrent2()
        mainPane.clear()
        mainPane.add(treeGroup)*/
    }
}

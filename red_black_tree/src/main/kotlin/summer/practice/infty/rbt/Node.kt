package summer.practice.infty.rbt

import javafx.beans.binding.BooleanExpression

/* Node of RedBlackTree
 * T - type of key, V - type of value
 */
class Node<T, V>(key: T,
                 var data: V?,
                 var parent: Node<T, V>? = null, // the link to the parent
                 var is_left: Boolean = false,
                 var is_sub_root: Boolean = false) {
    var key: T = key
        private set
    var is_red: Boolean = true // indicates is node is red
    var left: Node<T, V>? = null // the link to the left son
    var right: Node<T, V>? = null //the link to the right son

    fun changeSon(new_son: Node<T, V>?, is_left: Boolean){
        if(is_left) left = new_son
        else right = new_son
    }

    fun getSon(left: Boolean): Node<T, V>?{
        return if(left) this.left
               else this.right
    }

    fun invertLeft(){
        is_left = !is_left
    }

    fun swapKeyAndData(other: Node<T, V>?){
        if(other == null) return
        key = other.key.also { other.key = key }
        data = other.data.also { other.data = data }
    }

    // Override method toString() for debug output
    override fun toString(): String {
        return "Node($key: $data){is_red=$is_red, is_left=$is_left, " +
                "left=${left?.key ?: "null"}, " +
                "right=${right?.key ?: "null"}}, " +
                "parent=${parent?.key ?: "null"}}"
    }
}
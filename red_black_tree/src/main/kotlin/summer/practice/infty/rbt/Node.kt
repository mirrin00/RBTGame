package summer.practice.infty.rbt

/* Node of RedBlackTree
 * T - type of key, V - type of value
 */
class Node<T, V>(val key: T,
                 var data: V?,
                 var parent: Node<T, V>? = null, // the link to the parent
                 var is_left: Boolean = false) {
    var is_red: Boolean = true // indicates is node is red
    var left: Node<T, V>? = null // the link to the left son
    var right: Node<T, V>? = null //the link to the right son

    // Override method toString() for debug output
    override fun toString(): String {
        return "Node($key: $data){is_red=$is_red, is_left=$is_left, " +
                "left=${left?.hashCode() ?: "null"}, " +
                "right=${right?.hashCode() ?: "null"}}, " +
                "parent=${parent?.hashCode() ?: "null"}}"
    }
}
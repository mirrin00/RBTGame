package summer.practice.infty.rbt

class RedBlackTree<T: Comparable<T>, V> {
    private var root: Node<T, V>? = null // root of the tree
    var size: Int = 0 // FIXME: Is it needed
        private set

    // True - if node is red, otherwise - false
    private fun isRedNode(node: Node<T, V>?): Boolean{
        return node?.is_red ?: false
    }

    // True - if node is black, otherwise - false
    private fun isBlackNode(node: Node<T, V>?): Boolean{
        return !isRedNode(node)
    }

    // Find and return the value with key
    fun Find(key: T): V?{
        var cur = root
        while(cur != null){
            if(key == cur.key) break
            cur = if(key < cur.key) cur.left
            else cur.right
        }
        return cur?.data
    }

    // Insert the new value with key
    fun Insert(key: T, value: V?){
        if(root == null){
            size++
            root = Node(key, value)
            Balance(root)
            return
        }
        var cur = root
        while(cur != null){
            if(key == cur.key){
                cur.data = value
                return
            }
            if(key < cur.key){
                if(cur.left == null){
                    cur.left = Node(key, value, cur)
                    size++
                    Balance(cur.left)
                    return
                }
                else cur = cur.left
            }else{
                if(cur.right == null){
                    cur.right = Node(key, value, cur)
                    size++
                    Balance(cur.right)
                    return
                }
                else cur = cur.right
            }
        }
    }

    fun getData(): ArrayList<V?>{
        val values = ArrayList<V?>()
        fun inOrder(node: Node<T, V>?) {
            if (node == null) return
            inOrder(node.left)
            values.add(node.data)
            inOrder(node.right)
        }
        inOrder(root)
        return values
    }

    private fun Balance(node: Node<T, V>?){
        if(node == null) return
        Rotate(node)
        Recolor(node)
    }

    private fun Recolor(node: Node<T, V>){
        if(node.parent == null) node.is_red = false
        val parent = node.parent ?: return
        if(parent.parent == null) parent.is_red = false
        val grandparent = parent.parent ?: return
        val uncle = if(parent.is_left) grandparent.right
                    else grandparent.left
        if(isRedNode(parent) && isRedNode(uncle)){
            parent.is_red = false
            uncle?.is_red = false
            grandparent.is_red = true
            Balance(grandparent)
        }else if(isRedNode(parent)) parent.is_red = false
    }

    private fun Rotate(node: Node<T, V>){
        val parent = node.parent ?: return
        val grandparent = parent.parent ?: return
        val uncle = if(parent.is_left) grandparent.right
                    else grandparent.left
        if(isRedNode(parent) && isBlackNode(uncle)){
            if(node.is_left != parent.is_left){
                smallRotate(node)
                bigRotate(parent)
            }else bigRotate(node)
        }
    }

    private fun smallRotate(node: Node<T, V>){
        val parent = node.parent ?: return
        val grandparent = parent.parent ?: return
        node.parent = grandparent
        parent.parent = node
        if(parent.is_left){
            grandparent.left = node
            node.is_left = true
            parent.right = node.left
            node.left?.is_left = false
            node.left?.parent = parent
            parent.parent = node
            node.left = parent
        }else{
            grandparent.right = node
            node.is_left = false
            parent.left = node.right
            node.right?.is_left = true
            node.right?.parent = parent
            parent.parent = node
            node.right = parent
        }
    }

    private fun bigRotate(node: Node<T, V>) {
        val parent = node.parent ?: return
        val grandparent = parent.parent ?: return
        val uncle = if(parent.is_left) grandparent.right
                    else grandparent.left
        val parent_of_grandparent = grandparent.parent
        val was_left = grandparent.is_left
        if(parent.is_left){
            grandparent.left = parent.right
            parent.right = grandparent
        }else{
            grandparent.right = parent.left
            parent.left = grandparent
        }
        grandparent.is_left = !parent.is_left
        parent.parent = parent_of_grandparent
        if(was_left){
            parent_of_grandparent?.left = parent
            parent.is_left = true
        }else{
            parent_of_grandparent?.right = parent
            parent.is_left = false
        }
        if(parent_of_grandparent == null) root = parent
    }
}
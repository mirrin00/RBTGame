package summer.practice.infty.rbt

import summer.practice.infty.game.rooms.Room
import summer.practice.infty.game.generators.Generator
import summer.practice.infty.game.rooms.EmptyRoom

class RedBlackTreeGame<T: Comparable<T>>: RedBlackTree<T, Room>(){

    private fun regenerateRooms(node: Node<T, Room>?){
        if(node == null) return
        if(node.is_red != node.data?.element?.red)
            node.data?.regenerateRoom(node.is_red)
        regenerateRooms(node.left)
        regenerateRooms(node.right)
    }

    private fun getNodeAndDeep(key: T): Pair<Node<T, Room>?, Int>{
        var cur = root
        var deep = 0
        while(cur != null){
            if(key == cur.key) break
            cur = if(key < cur.key) cur.left
            else cur.right
            deep++
        }
        return Pair(cur, deep)
    }

    fun insertRooms(vararg keys: T){
        for(key in keys){
            super.insert(key, EmptyRoom())
            val (node, deep) = getNodeAndDeep(key)
            node?.data = Generator.generateRoom(deep)
        }
        regenerateRooms(root)
    }

    override fun insert(key: T, value: Room?) {
        super.insert(key, value)
        regenerateRooms(root)
    }

    override fun delete(key: T): Room? {
        val value = super.delete(key)
        regenerateRooms(root)
        return  value
    }
}
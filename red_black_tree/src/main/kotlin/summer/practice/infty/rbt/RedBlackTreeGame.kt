package summer.practice.infty.rbt

import summer.practice.infty.game.Room
import summer.practice.infty.game.generators.Generator

class RedBlackTreeGame<T: Comparable<T>>: RedBlackTree<T, Room>(){

    private fun regenerateRooms(node: Node<T, Room>?){
        if(node == null) return
        if(node.is_red != node.data?.element?.red)
            node.data?.regenerateRoom(node.is_red)
        regenerateRooms(node.left)
        regenerateRooms(node.right)
    }

    fun insertRooms(vararg keys: T){
        for(key in keys) super.insert(key, Generator.generateRoom())
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
package summer.practice.infty.rbt

import summer.practice.infty.game.rooms.Room
import summer.practice.infty.game.generators.Generator
import summer.practice.infty.game.rooms.EmptyRoom

// RBT extension for a game, where the V parameter is interface Room
class RedBlackTreeGame<T: Comparable<T>>: RedBlackTree<T, Room>(){

    // Regenerates room for specific color(red or black)
    private fun regenerateRooms(node: Node<T, Room>?){
        if(node == null) return
        if(node.is_red != node.data?.element?.red)
            node.data?.regenerateRoom(node.is_red)
        regenerateRooms(node.left)
        regenerateRooms(node.right)
    }

    // Returns Node by key and its depth
    private fun getNodeAndDepth(key: T): Pair<Node<T, Room>?, Int>{
        var cur = root
        var depth = 0
        while(cur != null){
            if(key == cur.key) break
            cur = if(key < cur.key) cur.left
            else cur.right
            depth++
        }
        return Pair(cur, depth)
    }

    // Inserts set of Rooms
    fun insertRooms(vararg keys: T){
        for(key in keys){
            super.insert(key, EmptyRoom())
            val (node, deep) = getNodeAndDepth(key)
            node?.data = Generator.generateRoom(deep)
        }
        regenerateRooms(root)
    }

    // Room insertion and rooms regeneration for color matching
    override fun insert(key: T, value: Room?) {
        super.insert(key, value)
        regenerateRooms(root)
    }

    // Room deletion and rooms regeneration for color matching
    override fun delete(key: T): Room? {
        val value = super.delete(key)
        regenerateRooms(root)
        return  value
    }

    // Returns room by key and it sons. All can be null
    fun getRoomWithSons(key: T): Triple<Room?, Room?, Room?>{
        var cur = root
        while(cur != null){
            if(key == cur.key) break
            cur = if(key < cur.key) cur.left
            else cur.right
        }
        return Triple(cur?.data, cur?.left?.data, cur?.right?.data)
    }
}
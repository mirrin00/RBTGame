package summer.practice.infty.rbt

import summer.practice.infty.game.rooms.Room
import summer.practice.infty.game.generators.Generator
import summer.practice.infty.game.rooms.EmptyRoom
import java.util.*
import kotlin.random.Random

// RBT extension for a game, where the V parameter is interface Room
class RedBlackTreeGame<T: Comparable<T>>: RedBlackTree<T, Room>(){

    private var root_depth = 0

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
        if(cur == null) depth = 0
        return Pair(cur, depth)
    }

    // Inserts set of Rooms
    fun insertRooms(vararg keys: T){
        for(key in keys) super.insert(key, EmptyRoom())
        for(key in keys){
            val (node, deep) = getNodeAndDepth(key)
            node?.data = Generator.generateRoom(root_depth + deep)
        }
        regenerateRooms(root)
    }

    // Deletes set of Rooms
    fun deleteRooms(vararg keys: T){
        for(key in keys) super.delete(key)
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
        val cur = getNodeAndDepth(key).first
        return Triple(cur?.data, cur?.left?.data, cur?.right?.data)
    }

    // Returns keys of sons
    fun getLeftRightKeys(key: T): Pair<T?, T?>{
        val cur = getNodeAndDepth(key).first
        return Pair(cur?.left?.key, cur?.right?.key)
    }

    // Returns the depth of node wby key
    fun getKeyDepth(key: T): Int{
        return getNodeAndDepth(key).second
    }

    fun getRandomKeyOnDepth(depth: Int): T?{
        if(depth <= 0) return null
        var cur_depth = 1
        var cur = root
        while(cur != null){
            if(cur_depth == depth) return cur.key
            if(cur.left == null && cur.right == null) return cur.key
            cur = if(Random.nextBoolean()){
                if(cur.left != null) cur.left else cur.right
            }else{
                if(cur.right != null) cur.right else cur.left
            }
            cur_depth++
        }
        return cur?.key
    }

    fun changeRoot(key: T){
        val (node, depth) = getNodeAndDepth(key)
        root_depth += depth
        root = node
        root?.parent = null
        root?.is_sub_root = true
    }

    fun getRootKey(): T?{
        return root?.key
    }
}
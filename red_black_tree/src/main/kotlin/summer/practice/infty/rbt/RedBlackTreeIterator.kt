package summer.practice.infty.rbt

import org.w3c.dom.ranges.RangeException
import summer.practice.infty.rbt.Node

// Iterator for RedBlackTree class
class RedBlackTreeIterator<T, V>(private var cur: Node<T, V>?): Iterator<V?>{
    // cur is a current node
    private var direct: Direction = getNextDirection() /* Direction for
                                                          the next element */

    // Returns the next direction for current node
    private fun getNextDirection(ignore_left: Boolean = false,
                                 ignore_right: Boolean = false): Direction{
        return if(cur == null) Direction.NONE
        else when{
            cur!!.left != null && !ignore_left  -> Direction.LEFT
            cur!!.right != null && !ignore_right -> Direction.RIGHT
            else -> if(cur!!.is_left) Direction.LEFT_UP else Direction.RIGHT_UP
        }
    }

    // Returns true, if has next element
    override fun hasNext(): Boolean {
        return cur != null
    }

    // Returns the value of the next element and changes cur to that element
    override fun next(): V? {
        val cur = cur ?: throw RangeException(1,
                                "Out of range, current element is null")
        val value = cur.data
        when(direct){
            Direction.LEFT  ->{
                this.cur = cur.left
                direct = getNextDirection()
            }
            Direction.RIGHT ->{
                this.cur = cur.right
                direct = getNextDirection()
            }
            Direction.NONE -> {}
            else ->{
                do{
                    this.cur = this.cur?.parent
                    direct = getNextDirection(true,
                                              direct == Direction.RIGHT_UP)
                }while(direct == Direction.LEFT_UP || direct == Direction.RIGHT_UP)
                if(direct == Direction.RIGHT){
                    this.cur = this.cur?.right
                    direct = getNextDirection()
                }
            }
        }
        return value
    }

    // Returns true, if the next element has a left son
    fun hasLeftSon() = cur?.left != null

    // Returns true, if the next element has a right son
    fun hasRightSon() = cur?.right != null

    // Returns true, if the next element is red
    fun isRed() = cur?.is_red ?: throw RangeException(1,
                                        "Check first by hasNext()")

    // Returns the key of the next element
    fun getKey() = cur?.key ?: throw RangeException(1,
                                        "Check first by hasNext()")

    // Returns true, if the next element is sub_root
    fun isSubRoot() = cur?.is_sub_root ?: throw RangeException(1,
                                        "Check first by hasNext()")
}

private enum class Direction{
    LEFT,
    RIGHT,
    LEFT_UP,
    RIGHT_UP,
    NONE
}
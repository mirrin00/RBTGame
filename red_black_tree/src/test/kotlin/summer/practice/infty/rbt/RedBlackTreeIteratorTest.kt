package summer.practice.infty.rbt

import org.junit.Test

import kotlin.collections.ArrayList
import kotlin.test.assertEquals

internal class RedBlackTreeIteratorTest{

    @Test
    fun testHasNext(){
        val emptyTree = RedBlackTree<Int, Int>()
        val notEmptyTree = RedBlackTree<Int, Int>()

        notEmptyTree.insert(1, 1)
        val iter = notEmptyTree.iterator()
        iter.next()

        assertEquals(false, emptyTree.iterator().hasNext())
        assertEquals(true, notEmptyTree.iterator().hasNext())
        assertEquals(false, iter.hasNext())
    }


    @Test
    fun testNext(){
        val tree = RedBlackTree<Int, Int>()
        val expectedArray = ArrayList<Int>(1000)
        val iteratedValues = ArrayList<Int>(1000)

        for(i in 1..1000){
            tree.insert(i, i)
            expectedArray.add(i)
        }

        val iter = tree.iterator()
        while(iter.hasNext()){
            iteratedValues.add(iter.next()!!)
        }

        assertEquals(expectedArray, iteratedValues.sorted())
    }
}
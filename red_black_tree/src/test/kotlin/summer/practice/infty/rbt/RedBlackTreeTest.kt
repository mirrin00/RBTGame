package summer.practice.infty.rbt

import org.junit.Test
import kotlin.test.assertEquals


internal class RedBlackTreeTest{

    @Test
    fun testInsert(){

        val valuesToInsert = LinkedHashMap<Int, Int>()
        val tree = RedBlackTree<Int, Int>()

        for(i in 1..1000){

            //Searching for random value not in tree yet
            var value = (-1000..1000).random()
            while(valuesToInsert.containsKey(value)){
                value = (-1000..1000).random()
            }

            valuesToInsert[value] = value
            tree.insert(value, value)
        }

        assertEquals(valuesToInsert.keys.sorted(), tree.getData().toList())
    }


    //@Ignore
    @Test
    fun testDelete(){

        val valuesToInsert = LinkedHashMap<Int, Int>()
        val tree = RedBlackTree<Int, Int>()

        for(i in 1..1000){

            //Searching for random value not in tree yet
            var value = (-1000..1000).random()
            while(valuesToInsert.containsKey(value)){
                value = (-1000..1000).random()
            }

            valuesToInsert[value] = value
            tree.insert(value, value)
        }

        for(i in 1..500){
            val value = (valuesToInsert.keys).random()
            valuesToInsert.remove(value, value)
            tree.delete(value)
        }

        assertEquals(valuesToInsert.keys.sorted(), tree.getData().toList())
    }

    @Test
    fun testFind(){

        val valuesAdded = LinkedHashMap<Int, Int>()
        val tree = RedBlackTree<Int, Int>()
        val emptyTree = RedBlackTree<Int, Int>()

        for(i in 1..1000){

            //Searching for random value not in tree yet
            var value = (-1000..1000).random()
            while(valuesAdded.containsKey(value)){
                value = (-1000..1000).random()
            }

            valuesAdded[value] = value
            tree.insert(value, value)
        }

        //Finding all keys in tree
        //Finding null in emptyTree
        for(value in valuesAdded.keys){
            assertEquals(value, tree.find(value))
            assertEquals(null, emptyTree.find(value))
        }

    }
}
package summer.practice.infty.game

import org.junit.Test
import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType
import kotlin.test.assertEquals

internal class PlayerTest{

    @Test
    fun getItemsCountInInventoryTest(){
        val playerWithEmptyInventory = Player(Game())
        val playerWithFullInventory = Player(Game())
        val playerWithThreeItems = Player(Game())

        val item = Item(Element.MARINE, ItemType.ARMOR, 10, Attributes.STRENGTH, 15, 15, 15, 0)

        for(i in 0 until playerWithFullInventory.getInventory().size){
            playerWithFullInventory.getInventory()[i] = item
        }
        for(i in 0 until 3){
            playerWithThreeItems.getInventory()[i] = item
        }


        assertEquals(0, playerWithEmptyInventory.getItemsCountInInventory())
        assertEquals(3, playerWithThreeItems.getItemsCountInInventory())
        assertEquals(playerWithFullInventory.getInventory().size, playerWithFullInventory.getItemsCountInInventory())
    }

    @Test
    fun addItemTest(){
        val playerWithEmptyInventory = Player(Game())
        val playerWithFullInventory = Player(Game())
        val playerWithOneSlot = Player(Game())

        val itemToAdd = Item(Element.MARINE, ItemType.ARMOR, 10, Attributes.STRENGTH, 15, 15, 15, 0)
        val otherItem = Item(Element.FROSTY, ItemType.AMULET, 10, Attributes.DEXTERITY, 10, 10, 10, 0)

        for(i in 0 until playerWithFullInventory.getInventory().size){
            playerWithFullInventory.getInventory()[i] = otherItem
        }

        for(i in playerWithOneSlot.getInventory()){
            playerWithOneSlot.addItem(otherItem)
        }
        playerWithOneSlot.getInventory()[0] = Item(Element.NONE, ItemType.EMPTY, 0, Attributes.NONE, 0, 0, 0 ,0)

        playerWithEmptyInventory.addItem(itemToAdd)
        playerWithFullInventory.addItem(itemToAdd)
        playerWithOneSlot.addItem(itemToAdd)



        for(i in 0 until playerWithEmptyInventory.getInventory().size){
            if(i == 0){
                assertEquals(itemToAdd, playerWithEmptyInventory.getInventory()[i])
            }else{
                assertEquals(ItemType.EMPTY, playerWithEmptyInventory.getInventory()[i].type)
            }
        }

        for(i in 0 until playerWithFullInventory.getInventory().size){
            assertEquals(otherItem, playerWithFullInventory.getInventory()[i])
        }

        for(i in 0 until playerWithOneSlot.getInventory().size){
            if(i == 0){
                assertEquals(itemToAdd, playerWithOneSlot.getInventory()[i])
            }else{
                assertEquals(otherItem, playerWithOneSlot.getInventory()[i])
            }
        }
    }

    @Test
    fun swapInventoryItemsTest(){
        val player = Player(Game())

        val item1 = Item(Element.MARINE, ItemType.ARMOR, 10, Attributes.STRENGTH, 15, 15, 15, 0)
        val item2 = Item(Element.FROSTY, ItemType.AMULET, 10, Attributes.DEXTERITY, 10, 10, 10, 1)
        val item3 = Item(Element.HEAVENLY, ItemType.HEALTH_POTION, 10, Attributes.DEXTERITY, 10, 10, 10, 2)
        val item4 = Item(Element.HELLISH, ItemType.OTHER, 10, Attributes.DEXTERITY, 10, 10, 10, 2)

        player.addItem(item1)
        player.addItem(item2)
        player.addItem(item3)

        player.swapInventoryItems(item1, item2)
        player.swapInventoryItems(item3, item4)

        assertEquals(item2, player.getInventory()[0])
        assertEquals(item1, player.getInventory()[1])
        assertEquals(item3, player.getInventory()[2])
    }
}
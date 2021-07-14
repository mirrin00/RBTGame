package summer.practice.infty.game.creatures

import org.junit.Test
import summer.practice.infty.game.Attributes
import summer.practice.infty.game.Element
import summer.practice.infty.game.Game
import summer.practice.infty.game.Player
import summer.practice.infty.game.creatures.Dragon
import summer.practice.infty.game.items.Item
import summer.practice.infty.game.items.ItemType
import summer.practice.infty.game.items.generateEmptyItem
import java.lang.reflect.Field
import kotlin.test.assertEquals

internal class CreatureTest{
    @Test
    fun getCreatureAttackTest() {
        val player = Player(Game())
        val dragon = Dragon(Element.HEAVENLY, 1000000, 50)

        player.addItem(Item(Element.FROSTY, ItemType.WEAPON, 10000, Attributes.STRENGTH, 50, 1, 1, 1, 10, 0))
        player.addItem(Item(Element.FROSTY, ItemType.ARMOR, 100, Attributes.STRENGTH, 50, 1, 1, 1, 10, 0))
        player.setActiveItem(player.getInventory()[0])
        player.setActiveItem(player.getInventory()[1])

        dragon.attack(player)

        assertEquals((1000000 - 10 * 100 * 0.2 * 0.5).toInt(), dragon.health)
        assertEquals((100 - 50 * 1.5 + 100 * 0.2).toInt(), player.health)
    }
}
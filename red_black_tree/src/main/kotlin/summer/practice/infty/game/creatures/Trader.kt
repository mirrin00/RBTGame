package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionBuy
import summer.practice.infty.actions.ActionNext
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player
import summer.practice.infty.game.items.Item

class Trader(item1: Item,
             item2: Item,
             item3: Item): Creature{
    override val element: Element = Element.NONE
    override var health: Int = 1
        set(value){field = 1}
    override val damage: Int = 0
    override var in_battle: Boolean = false
        set(value) {field = false}
    override val description: String = "You meet the trader. He is ready to trade with you. The Trader has:" +
                                       "\n${item1.string_type}\n${item2.string_type}\n${item3.string_type}"
    override val win_description: String = "Trader has left you. You continues along the trail."
    private val items = arrayOf(item1, item2, item3)

    override fun attack(player: Player) {}

    override fun getDamageRatio(magic: Boolean): Double = 0.0

    override fun getActions(player: Player): ArrayList<Action> {
        val actions = ArrayList<Action>()
        actions.add(ActionBuy(0, true,"Buy first item", items[0].tip))
        actions.add(ActionBuy(1, true,"Buy second item", items[1].tip))
        actions.add(ActionBuy(2, true,"Buy third item", items[3].tip))
        actions.add(ActionNext(true,"Leave the Trader"))
        return  actions
    }

    fun buy(index: Int, player: Player){
        when(index){
            in items.indices -> {
                if(player.coins < items[index].price) return
                player.coins -= items[index].price
                player.addItem(items[index])
            }
        }
    }
}
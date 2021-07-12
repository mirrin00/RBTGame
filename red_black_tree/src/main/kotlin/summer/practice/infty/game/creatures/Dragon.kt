package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionFight
import summer.practice.infty.game.Element

class Dragon(override val element: Element,
             override var health: Int,
             override val damage: Int,): Creature{
    override var in_battle: Boolean = true
    override val description: String = "The large shadow has moved over you. It's a Dragon! " +
                                       "It is an ancient, mysterious creature, and now you " +
                                       "must fight with him."
    override val win_description: String = "You have defeated the ancient creature. You can move on."

    override fun getDamageRatio(magic: Boolean): Double = if(magic) 0.7 else 1.0

    override fun getActions(): ArrayList<Action> {
        val actions = ArrayList<Action>()
        actions.add(ActionFight(false, "Fight by weapon"))
        actions.add(ActionFight(true, "Fight by magic"))
        return  actions
    }
}
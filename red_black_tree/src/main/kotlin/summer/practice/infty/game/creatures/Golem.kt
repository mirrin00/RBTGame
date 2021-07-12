package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionFight
import summer.practice.infty.game.Element

class Golem(override val element: Element,
            override var health: Int,
            override val damage: Int,): Creature{
    override var in_battle: Boolean = true
    override val description: String = "You hear the loud footsteps. It's a Golem! " +
                                       "This is a strong opponent, his body is like a stone."
    override val win_description: String = "The big lump has been fallen. You can move on."

    override fun getDamageRatio(magic: Boolean): Double = if(magic) 1.0 else 0.7

    override fun getActions(): ArrayList<Action> {
        val actions = ArrayList<Action>()
        actions.add(ActionFight(false, "Fight by weapon"))
        actions.add(ActionFight(true, "Fight by magic"))
        return  actions
    }
}
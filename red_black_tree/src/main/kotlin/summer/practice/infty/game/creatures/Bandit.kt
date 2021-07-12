package summer.practice.infty.game.creatures

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionFight
import summer.practice.infty.actions.ActionPay
import summer.practice.infty.actions.ActionToFight
import summer.practice.infty.game.Element
import summer.practice.infty.game.Player

class Bandit(override val element: Element,
             override var health: Int,
             override val damage: Int,
             val pay_cost: Int): Creature{
    override var in_battle: Boolean = false
    override val description: String = "The bandit ambushed you while you were walking along the trail. " +
                                       "You can pay him $pay_cost coins to let you go, or you can fight him"
    override val win_description: String = "The Bandit has been killed. You can move on."

    override fun getDamageRatio(magic: Boolean): Double = 1.0

    override fun getActions(): ArrayList<Action>{
        val actions = ArrayList<Action>()
        if(in_battle){
            actions.add(ActionFight(false, "Fight by weapon"))
            actions.add(ActionFight(true, "Fight by magic"))
        }else{
            actions.add(ActionPay("Pay $pay_cost to avoid fight"))
            actions.add(ActionToFight("Fight with Bandit"))
        }
        return actions
    }
}
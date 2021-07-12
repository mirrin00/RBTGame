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
package summer.practice.infty.game

import kotlin.math.pow

class Game {
    val player = Player()

    companion object{
        var deep: Int = 0
            private set
        fun getRatioFromDeep(local_deep: Int = deep): Double{
            val base = 1.5
            val ratio = 2.75
            return ratio * base.pow(local_deep.toDouble())
        }
    }
}
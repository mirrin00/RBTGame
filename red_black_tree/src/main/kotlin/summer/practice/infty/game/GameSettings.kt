package summer.practice.infty.game

object GameSettings{
    var difficulty: Difficulty = Difficulty.NORMAL

    fun getDepthFactor() = when(difficulty){
        Difficulty.NORMAL -> 1.5
        Difficulty.HARD -> 2.0
    }

    fun getDepthPow() = when(difficulty){
        Difficulty.NORMAL -> 1.5
        Difficulty.HARD -> 2.0
    }
}

enum class Difficulty{
    NORMAL,
    HARD,
}
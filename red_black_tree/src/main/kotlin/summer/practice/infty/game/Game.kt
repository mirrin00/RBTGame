package summer.practice.infty.game

class Game {
    val player = Player()
    private var cur_stage = Stages.WAY

    fun next(){
        cur_stage = when(cur_stage){
            Stages.CREATURE -> Stages.EVENT
            Stages.EVENT -> Stages.WAY
            Stages.WAY -> Stages.CREATURE
        }
    }

    // Debug method
    fun debugStageDescription() = when(cur_stage){
        Stages.CREATURE -> "It's description of creature stage"
        Stages.EVENT -> "It's description of event"
        Stages.WAY -> "It's description of ways"
    }
}

private enum class Stages{
    CREATURE,
    EVENT,
    WAY
}
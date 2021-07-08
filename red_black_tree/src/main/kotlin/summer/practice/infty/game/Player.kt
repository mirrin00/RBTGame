package summer.practice.infty.game

class Player{
    // TODO("Not yet implemented")
    var coins: Int = 0
        private set
    var max_health = 100 // TODO: Private field??
    var health: Int = max_health
        set(value){
            field = when{
                value < 0 -> 0
                value > max_health -> max_health
                else -> value
            }
        }
    var max_magic = 100 // TODO: Private field??
    var magic: Int = max_magic
        set(value){
            field = when{
                value < 0 -> 0
                value > max_health -> max_health
                else -> value
            }
        }
    object Attributes{
        var perception: Int = 0
            private set
    }

    fun changeCoins(change_count: Int){
        coins += change_count
    }
}
package summer.practice.infty.game

class Player{
    // TODO("Not yet implemented")
    var coins: Int = 0
        private set
    object Attributes{
        var perception: Int = 0
            private set
    }

    fun changeCoins(change_count: Int){
        coins += change_count
    }
}
package summer.practice.infty.game.generators

import summer.practice.infty.game.Room

internal interface RoomFabric{
    fun generateRoom(): Room
}
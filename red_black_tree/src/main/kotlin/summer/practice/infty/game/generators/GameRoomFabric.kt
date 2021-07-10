package summer.practice.infty.game.generators

import summer.practice.infty.game.rooms.Room
import summer.practice.infty.game.rooms.UsualRoom

internal class GameRoomFabric: RoomFabric{
    override fun generateRoom(deep_level: Int): Room = UsualRoom(deep_level)
}
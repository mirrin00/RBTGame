package summer.practice.infty.game.generators

import summer.practice.infty.game.events.*
import kotlin.RuntimeException
import kotlin.random.Random

internal class UsualRoomEventFabric: RoomEventFabric{
    private fun generateKCount(depth_level: Int) = (Generator.getRandomFromDeep(depth_level) * k_ratio * Random.nextDouble(0.25, 1.0)).toInt()

    private fun generateCoinsCount(depth_level: Int) = (Generator.getRandomFromDeep(depth_level) * Random.nextDouble(2.0, 5.0)).toInt()

    private fun generateInnCost(depth_level: Int) = (Generator.getRandomFromDeep(depth_level) * Random.nextDouble(1.0, 4.0)).toInt()

    private fun generateInnRegain() = 10 * Random.nextInt(8, 20)

    override fun generateRoomEvent(depth_level: Int): RoomEvent = when((0..3).random()){
            0 -> Tracks(generateKCount(depth_level))
            1 -> Inn(generateKCount(depth_level), generateInnCost(depth_level), generateInnRegain())
            2 -> Portal(generateKCount(depth_level))
            3 -> Treasure(generateKCount(depth_level), generateCoinsCount(depth_level))
            else -> throw RuntimeException("Wrong random number: there is no event for it")
        }
    companion object{
        private const val k_ratio: Double = 0.75
    }
}
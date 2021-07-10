package summer.practice.infty.game.generators

import summer.practice.infty.game.events.*
import kotlin.RuntimeException
import kotlin.random.Random

internal class UsualRoomEventFabric: RoomEventFabric{
    private fun generateKCount(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * k_ratio * Random.nextDouble(0.25, 1.0)).toInt()

    private fun generateCoinsCount(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * Random.nextDouble(2.0, 5.0)).toInt()

    private fun generateInnCost(deep_level: Int) = (Generator.getRandomFromDeep(deep_level) * Random.nextDouble(1.0, 4.0)).toInt()

    private fun generateInnRegain() = 10 * Random.nextInt(8, 20)

    override fun generateRoomEvent(deep_level: Int): RoomEvent = when((0..3).random()){
            0 -> Tracks(generateKCount(deep_level))
            1 -> Inn(generateKCount(deep_level), generateInnCost(deep_level), generateInnRegain())
            2 -> Portal(generateKCount(deep_level))
            3 -> Treasure(generateKCount(deep_level), generateCoinsCount(deep_level))
            else -> throw RuntimeException("Wrong random number: there is no event for it")
        }
    companion object{
        private const val k_ratio: Double = 0.75
    }
}
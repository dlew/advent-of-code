import com.google.common.math.IntMath.pow
import kotlin.math.log10

object Day11 {
  fun part1(input: String) = solve(input, 25)

  fun part2(input: String) = solve(input, 75)

  private fun solve(input: String, blinks: Int): Long {
    val stones = input.splitWhitespace().toLongList()
    val memo = mutableMapOf<State, Long>()
    return stones.sumOf { stone -> countStones(memo, State(stone, blinks)) }
  }

  private data class State(val stone: Long, val blinks: Int)

  private fun countStones(memo: MutableMap<State, Long>, state: State): Long {
    val (stone, blinks) = state
    if (blinks == 0) return 1L
    return memo.getOrPut(state) {
      if (stone == 0L) {
        return@getOrPut countStones(memo, State(1, blinks - 1))
      }

      val numDigits = stone.numDigits()
      if (numDigits % 2 == 0) {
        val size = pow(10, numDigits / 2)
        return@getOrPut countStones(memo, State(stone / size, blinks - 1)) +
            countStones(memo, State(stone % size, blinks - 1))
      }

      return@getOrPut countStones(memo, State(stone * 2024, blinks - 1))
    }
  }

  private fun Long.numDigits() = log10(this.toDouble()).toInt() + 1
}
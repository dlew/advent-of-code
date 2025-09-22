import utils.splitNewlines
import utils.toIntList

object Day10 {
  fun part1(input: String): Int {
    var one = 0
    var three = 0
    getAdapters(input)
      .zipWithNext()
      .forEach { (a, b) -> if (b - a == 1) one++ else three++ }
    return one * three
  }

  fun part2(input: String) = numCombinations(getAdapters(input), 0, mutableMapOf())

  private fun numCombinations(adapters: List<Int>, pos: Int, memo: MutableMap<Int, Long>): Long {
    if (pos == adapters.size - 1) return 1

    if (pos in memo) return memo[pos]!!

    val voltage = adapters[pos]
    memo[pos] = adapters
      .drop(pos + 1)
      .takeWhile { it - voltage <= 3 }
      .indices
      .sumOf { index ->
        numCombinations(adapters, pos + index + 1, memo)
      }
    return memo[pos]!!
  }

  private fun getAdapters(input: String): List<Int> {
    val adapters = input.splitNewlines().toIntList().sorted()
    return listOf(0) + adapters + listOf(adapters.last() + 3)
  }
}
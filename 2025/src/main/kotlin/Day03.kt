import utils.splitNewlines

object Day03 {
  fun part1(input: String) = solve(input, 2)

  fun part2(input: String) = solve(input, 12)

  private fun solve(input: String, digits: Int) = input.splitNewlines().sumOf { joltage(it, digits) }

  private fun joltage(bank: String, digits: Int): Long {
    val batteriesByJoltage = bank.withIndex().sortedByDescending { it.value }.toMutableList()
    val enabledBatteries = mutableListOf<IndexedValue<Char>>()
    repeat(digits) {
      val nextIndex = nextBatteryIndex(batteriesByJoltage, enabledBatteries)
      enabledBatteries.add(batteriesByJoltage.removeAt(nextIndex))
    }
    return enabledBatteries.sortedBy { it.index }.joinToString("") { it.value.toString() }.toLong()
  }

  private fun nextBatteryIndex(
    batteriesByJoltage: List<IndexedValue<Char>>,
    enabledBatteries: List<IndexedValue<Char>>,
  ): Int {
    // Find the best battery to the right of any enabled battery, starting with the smallest digit
    enabledBatteries.asReversed().forEach { enabledBattery ->
      val next = batteriesByJoltage.indexOfFirst { it.index > enabledBattery.index }
      if (next != -1) return next
    }
    return 0 // There were no batteries to the right of any enabled batteries, pick best next battery to enable
  }
}

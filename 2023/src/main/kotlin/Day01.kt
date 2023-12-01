object Day01 {

  private val NUMBER_REGEX = Regex("\\d")
  private val NUMBER_AND_TEXT_REGEX = Regex("(?=(one|two|three|four|five|six|seven|eight|nine|\\d))")

  fun part1(input: String): Int {
    return input.splitNewlines()
      .map { line -> NUMBER_REGEX.findAll(line).map { it.value.toInt() } }
      .sumCalibrationValues()
  }

  fun part2(input: String): Int {
    return input.splitNewlines()
      .map { line ->
        NUMBER_AND_TEXT_REGEX.findAll(line).map {
          when (val value = it.groups[1]!!.value) {
            "one" -> 1
            "two" -> 2
            "three" -> 3
            "four" -> 4
            "five" -> 5
            "six" -> 6
            "seven" -> 7
            "eight" -> 8
            "nine" -> 9
            else -> value.toInt()
          }
        }
      }
      .sumCalibrationValues()
  }

  private fun List<Sequence<Int>>.sumCalibrationValues() = this.sumOf { it.first() * 10 + it.last() }
}

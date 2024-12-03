object Day03 {
  fun part1(input: String) = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
    .findAll(input)
    .sumOf { it.multiply() }

  fun part2(input: String) = Regex("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")
    .findAll(input)
    .fold(0 to true) { (total, active), matchResult ->
      when (matchResult.groupValues[0]) {
        "do()" -> total to true
        "don't()" -> total to false
        else -> total + (if (active) matchResult.multiply() else 0) to active
      }
    }
    .first

  private fun MatchResult.multiply() = groupValues[1].toInt() * groupValues[2].toInt()
}

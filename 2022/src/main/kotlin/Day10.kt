object Day10 {

  fun part1(input: String): Int {
    return parsePositions(input)
      .foldIndexed(0) { index, acc, x -> if (index % 40 == 19) acc + (1 + index) * x else acc }
  }

  fun part2(input: String): String {
    return parsePositions(input)
      .take(240)
      .mapIndexed { crt, sprite -> if (crt % 40 in (sprite - 1)..(sprite + 1)) '#' else '.' }
      .chunked(40)
      .joinToString("\n") { it.joinToString("") }
  }

  private fun parsePositions(input: String): List<Int> {
    return input.splitNewlines()
      .flatMap { if (it == "noop") listOf(0) else listOf(0, it.drop(5).toInt()) }
      .scan(1) { acc, value -> acc + value }
  }

}
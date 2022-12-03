object Day03 {

  fun part1(input: String): Int {
    return input
      .splitNewlines()
      .map { line -> line.chunked(line.length / 2) }
      .map { sacks -> sacks.findCommonItem() }
      .sumOf { it.priority }
  }

  fun part2(input: String): Int {
    return input
      .splitNewlines()
      .chunked(3)
      .map { sacks -> sacks.findCommonItem() }
      .sumOf { it.priority }
  }

  private fun List<String>.findCommonItem(): Char {
    return map { it.toCharArray().toSet() }
      .reduce { acc, chars -> acc.intersect(chars) }
      .toCharArray()[0]
  }

  private val Char.priority: Int
    get() = if (code >= 97) code - 96 else code - 38

}

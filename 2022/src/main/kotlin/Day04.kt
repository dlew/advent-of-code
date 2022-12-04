object Day04 {

  fun part1(input: String): Int {
    return parse(input).count { (a, b) -> a.containsAll(b) || b.containsAll(a) }
  }

  fun part2(input: String): Int {
    return parse(input).count { (a, b) -> a.containsAny(b) }
  }

  private fun IntRange.containsAll(other: IntRange) = this.first <= other.first && this.last >= other.last

  private fun IntRange.containsAny(other: IntRange) = this.intersect(other).isNotEmpty()

  private fun parse(input: String): List<Pair<IntRange, IntRange>> {
    return input
      .splitNewlines()
      .flatMap { line ->
        line.splitCommas()
          .map { it.toRange() }
          .zipWithNext()
      }
  }

  private fun String.toRange(): IntRange {
    val split = split("-").toIntList()
    return IntRange(split[0], split[1])
  }

}
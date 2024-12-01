import kotlin.math.abs

object Day01 {
  fun part1(input: String): Int {
    val (left, right) = parse(input)
    return left.sorted()
      .zip(right.sorted())
      .sumOf { (l, r) -> abs(l - r) }
  }

  fun part2(input: String): Int {
    val (left, right) = parse(input)
    val counts = right.groupingBy { it }.eachCount()
    return left.sumOf { l -> l * counts.getOrDefault(l, 0) }
  }

  private fun parse(input: String) = input.splitNewlines()
    .map { line ->
      val (left, right) = line.splitWhitespace().toIntList()
      return@map left to right
    }.unzip()
}

import utils.splitNewlines

object Day05 {
  fun part1(input: String): Int {
    val (ranges, ingredients) = parse(input)
    return ingredients.count { ingredient ->
      ranges.any { range -> ingredient in range }
    }
  }

  fun part2(input: String): Long {
    val (ranges) = parse(input)
    val sortedRanges = ranges.sortedWith(compareBy({ it.first }, { it.last }))
    var max = 0L
    var count = 0L
    sortedRanges.forEach { range ->
      if (range.first > max) {
        count += range.last - range.first + 1
      } else if (range.last > max) {
        count += range.last - max
      }
      max = range.last
    }
    return count
  }

  private fun parse(input: String): Pair<List<LongRange>, List<Long>> {
    val (ranges, ingredients) = input.split("\n\n")
    val ingredientRanges = ranges.splitNewlines().map { line ->
      val (start, end) = line.split("-")
      start.toLong()..end.toLong()
    }
    val ingredientLongs = ingredients.splitNewlines().map { it.toLong() }
    return ingredientRanges to ingredientLongs
  }
}
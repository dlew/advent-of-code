object Day02 {
  fun part1(input: String) = input.toReports().count { it.isSafe() }

  fun part2(input: String) = input.toReports().count { it.isSafeWithDampener() }

  private fun List<Int>.isSafe(): Boolean {
    val diffs = this.zipWithNext { a, b -> a - b }
    return diffs.all { it in 1..3 } || diffs.all { it in -3..-1 }
  }

  private fun List<Int>.isSafeWithDampener() = this.indices.any { index ->
    (this.subList(0, index) + this.subList(index + 1, this.size)).isSafe()
  }

  private fun String.toReports() = this.splitNewlines().map { it.splitWhitespace().toIntList() }
}

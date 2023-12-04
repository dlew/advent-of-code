object Day04 {

  fun part1(input: String): Int {
    return parseScores(input)
      .sumOf { if (it == 0) 0 else 1 shl (it - 1) }
  }

  fun part2(input: String): Int {
    val scores = parseScores(input)
    val memo = IntArray(scores.size)
    scores.indices.reversed().forEach {
      memo[it] = 1 + memo.slice(it + 1..it + scores[it]).sum()
    }
    return memo.sum()
  }

  private fun parseScores(input: String): List<Int> {
    val regex = Regex(".+: (.+) \\| (.+)")
    return input.splitNewlines()
      .map {
        val match = regex.matchEntire(it)!!
        val winningNumbers = match.groupValues[1].splitWhitespace().toIntList()
        val numbersYouHave = match.groupValues[2].splitWhitespace().toIntList().toSet()
        return@map winningNumbers.intersect(numbersYouHave).size
      }
  }
}
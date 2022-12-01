object Day01 {
  fun topElf(input: String): Int {
    return parseElves(input).max()
  }

  fun topThreeElves(input: String): Int {
    return parseElves(input).sortedDescending().take(3).sum()
  }

  private fun parseElves(input: String): List<Int> {
    return input
      .split("\n\n")
      .map { elf ->
        elf.splitNewlines()
          .toIntList()
          .sum()
      }
  }
}

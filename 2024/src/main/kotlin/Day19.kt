object Day19 {
  fun part1(input: String) = arrangementsPerDesign(input).count { it != 0L }

  fun part2(input: String) = arrangementsPerDesign(input).sum()

  private fun arrangementsPerDesign(input: String): List<Long> {
    val (patterns, designs) = input.split("\n\n")
    val memo = mutableMapOf<String, Long>()
    val patternMap = patterns.splitCommas().groupBy { it.first() }
    return designs.splitNewlines().map { numDesigns(it, patternMap, memo) }
  }

  private fun numDesigns(
    design: String,
    patterns: Map<Char, List<String>>,
    memo: MutableMap<String, Long>,
  ): Long {
    if (design.isEmpty()) return 1L
    return memo.getOrPut(design) {
      patterns[design[0]].orEmpty()
        .filter { design.startsWith(it) }
        .sumOf { numDesigns(design.drop(it.length), patterns, memo) }
    }
  }
}
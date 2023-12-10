object Day09 {

  fun part1(input: String): Int {
    return parseInput(input)
      .map { sequences(it) }
      .sumOf { extrapolate(it) }
  }

  fun part2(input: String): Int {
    return parseInput(input)
      .map { sequences(it) }
      .sumOf { extrapolateBackwards(it) }
  }

  private fun sequences(history: List<Int>): List<List<Int>> {
    val sequences = mutableListOf(history)
    while (!sequences.last().all { it == 0 }) {
      sequences.add(sequences.last().zipWithNext().map { (a, b) -> b - a })
    }
    return sequences
  }

  private fun extrapolate(sequences: List<List<Int>>) = sequences.sumOf { it.last() }

  private fun extrapolateBackwards(sequences: List<List<Int>>) =
    sequences.foldRight(0) { seq, acc -> seq.first() - acc }

  private fun parseInput(input: String) = input.splitNewlines().map { it.splitWhitespace().toIntList() }

}
import utils.splitNewlines

object Day06 {
  fun part1(input: String): Int {
    return parse(input)
      .map { it.flatten().toSet() }
      .sumOf { it.size }
  }

  fun part2(input: String): Int {
    return parse(input)
      .map { group -> group.reduce { a, b -> a intersect b } }
      .sumOf { it.size }
  }

  private fun parse(input: String): List<Set<Set<Char>>> {
    return input.split("\n\n").map { forms ->
      forms.splitNewlines().map { line ->
        line.toCharArray().toSet()
      }.toSet()
    }
  }
}
import java.util.regex.Pattern

object Day13 {

  fun part1(input: String) = solve(input, ::findHorizontalReflection)

  fun part2(input: String) = solve(input, ::findSmudgedHorizontalReflection)

  private fun solve(input: String, finder: (List<String>) -> Int?): Int {
    return parseInput(input)
      .sumOf { pattern ->
        val horizontal = finder(pattern)
        if (horizontal != null) 100 * horizontal else finder(rotateClockwise(pattern))!!
      }
  }

  private fun findHorizontalReflection(pattern: List<String>): Int? {
    val height = pattern.size
    return (1..<height)
      .find { y ->
        val size = minOf(y, height - y)
        val above = pattern.subList(y - size, y)
        val below = pattern.subList(y, y + size).reversed()
        return@find above == below
      }
  }

  private fun findSmudgedHorizontalReflection(pattern: List<String>): Int? {
    val height = pattern.size
    return (1..<height)
      .find { y ->
        val size = minOf(y, height - y)
        val above = pattern.subList(y - size, y)
        val below = pattern.subList(y, y + size).reversed()

        // Require one row to differ
        val differentRows = (0..<size).filter { above[it] != below[it] }
        if (differentRows.size != 1) {
          return@find false
        }

        // Require one smudge in the one row that differs
        val smudgeRow = differentRows[0]
        return@find canSmudge(above[smudgeRow], below[smudgeRow])
      }
  }

  private fun rotateClockwise(pattern: List<String>): List<String> {
    return pattern[0].indices.map { col ->
      pattern.map { it[col] }.joinToString("")
    }
  }

  private fun canSmudge(a: String, b: String) = a.indices.count { a[it] != b[it] } == 1

  private fun parseInput(input: String): List<List<String>> {
    return input.split(Pattern.compile("\\r?\\n\\r?\\n")).map { it.splitNewlines() }
  }
}
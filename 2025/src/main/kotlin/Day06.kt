import utils.splitNewlines
import utils.splitWhitespace

object Day06 {
  private data class Problem(val numbers: List<Long>, val operation: Char)

  fun part1(input: String): Long {
    val worksheet = input.splitNewlines().map { line -> line.splitWhitespace() }
    val problems = worksheet[0].indices.map { row ->
      Problem(
        numbers = (0 until worksheet.size - 1)
          .map { col -> worksheet[col][row].toLong() },
        operation = worksheet.last()[row].first()
      )
    }
    return solveProblems(problems)
  }

  fun part2(input: String): Long {
    val problems = mutableListOf<Problem>()

    val collection = mutableListOf<String>()
    fun collectionToProblem(): Problem {
      val numbers = collection.map { it.dropLast(1).trim().toLong() }
      val operation = collection[0].last()
      return Problem(numbers, operation)
    }

    val lines = input.split("\n") // Explicitly keep trailing whitespace
    val line: StringBuilder = StringBuilder()
    lines[0].indices.forEach { x ->
      lines.indices.forEach { y -> line.append(lines[y][x]) }

      if (line.isBlank()) {
        problems.add(collectionToProblem())
        collection.clear()
      } else {
        collection.add(line.toString())
      }

      line.clear()
    }

    problems.add(collectionToProblem())

    return solveProblems(problems)
  }

  private fun solveProblems(problems: List<Problem>): Long {
    return problems.sumOf { (numbers, operation) ->
      numbers.reduce(if (operation == '+') Long::plus else Long::times)
    }
  }
}
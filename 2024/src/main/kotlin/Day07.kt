import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow

object Day07 {
  fun part1(input: String) = solve(input, false)

  fun part2(input: String) = solve(input, true)

  private fun solve(input: String, allowConcatenation: Boolean): Long {
    return parse(input)
      .parallelStream()
      .filter { canCalibrate(it.test, it.numbers, allowConcatenation) }
      .mapToLong { it.test }
      .sum()
  }

  private enum class Operator {
    ADD,
    MULTIPLY,
    CONCATENATE
  }

  private data class Equation(val test: Long, val numbers: List<Long>)

  private fun canCalibrate(test: Long, numbers: List<Long>, allowConcatenation: Boolean): Boolean {
    // Base cases / early exits
    when {
      numbers.size == 1 -> return test == numbers[0]
      test < numbers[0] -> return false
    }

    // Try all possible operators on the equation
    return Operator.entries.any { op ->
      val (a, b) = numbers
      val eval = when (op) {
        Operator.ADD -> a + b
        Operator.MULTIPLY -> a * b
        Operator.CONCATENATE -> {
          if (!allowConcatenation) return@any false
          // No, I didn't think of this myself, but I think it's a cool optimization
          (a * 10.0.pow(ceil(log(b + 1.0, 10.0))) + b).toLong()
        }
      }
      return@any canCalibrate(test, listOf(eval) + numbers.drop(2), allowConcatenation)
    }
  }

  private fun parse(input: String): List<Equation> {
    return input.splitNewlines().map { line ->
      val (test, numbers) = line.split(":")
      return@map Equation(test.toLong(), numbers.splitWhitespace().toLongList())
    }
  }
}
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

  private data class Equation(val test: Long, val numbers: List<Long>)

  private fun canCalibrate(test: Long, numbers: List<Long>, allowConcatenation: Boolean): Boolean {
    val last = numbers.last()

    if (numbers.size == 1) {
      return test == last
    }

    if (allowConcatenation) {
      val offset = 10.0.pow(ceil(log(last + 1.0, 10.0))).toLong()
      if (test % offset == last && canCalibrate(test / offset, numbers.dropLast(1), true)) {
        return true
      }
    }

    if (test % last == 0L && canCalibrate(test / last, numbers.dropLast(1), allowConcatenation)) {
      return true
    }

    return canCalibrate(test - last, numbers.dropLast(1), allowConcatenation)
  }

  private fun parse(input: String): List<Equation> {
    return input.splitNewlines().map { line ->
      val (test, numbers) = line.split(":")
      return@map Equation(test.toLong(), numbers.splitWhitespace().toLongList())
    }
  }
}
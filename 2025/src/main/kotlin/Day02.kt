import utils.splitCommas
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

object Day02 {
  fun part1(input: String) = solve(input, ::invalidIdPart1)

  fun part2(input: String) = solve(input, ::invalidIdPart2)

  private fun solve(input: String, invalidId: (Long) -> Boolean): Long {
    return input
      .splitCommas()
      .map { range ->
        val (start, end) = range.split("-")
        return@map LongRange(start.toLong(), end.toLong())
      }
      .flatMap { it.asIterable() }
      .parallelStream() // Vroom
      .filter { invalidId(it) }
      .reduce(0, Long::plus)
  }

  private fun invalidIdPart1(id: Long): Boolean {
    val numDigits = id.numDigits()
    return repeats(id, (numDigits / 2.0).roundToInt(), numDigits)
  }

  private fun invalidIdPart2(id: Long): Boolean {
    val numDigits = id.numDigits()
    return (1..numDigits / 2)
      .any { sequenceLength -> repeats(id, sequenceLength, numDigits) }
  }

  private fun repeats(id: Long, sequenceLength: Int, numDigits: Int): Boolean {
    // If it's not evenly divisible by the sequence (or isn't divisible), we know it can't repeat
    if (numDigits == 1 || numDigits % sequenceLength != 0) return false

    // Check that each `sequenceLength` characters are equivalent to each other
    val mask = (10.0.pow(sequenceLength)).toLong()
    val pattern = id % mask
    var remainder = id / mask
    while (remainder != 0L) {
      if (remainder % mask != pattern) return false
      remainder /= mask
    }

    return true
  }

  private fun Long.numDigits() = (log10(this.toDouble()) + 1).toInt()
}
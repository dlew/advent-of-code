import models.XY
import utils.splitNewlines
import kotlin.math.ceil

object Day05 {
  fun part1(input: String) = sortedSeatIds(input).last()

  fun part2(input: String): Int {
    return sortedSeatIds(input)
      .zipWithNext()
      .find { it.first + 2 == it.second }!!
      .first + 1
  }

  private fun sortedSeatIds(input: String): List<Int> {
    return input.splitNewlines()
      .map { binarySeat(it) }
      .map { seatId(it) }
      .sorted()
  }

  private fun binarySeat(code: String, minX: Int = 0, maxX: Int = 7, minY: Int = 0, maxY: Int = 127): XY {
    if (code.isEmpty()) return XY(minX, minY)

    return when (code.first()) {
      'F' -> binarySeat(code.drop(1), minX, maxX, minY, maxY - ceil((maxY - minY) / 2.0).toInt())
      'B' -> binarySeat(code.drop(1), minX, maxX, minY + ceil((maxY - minY) / 2.0).toInt(), maxY)
      'L' -> binarySeat(code.drop(1), minX, maxX - ceil((maxX - minX) / 2.0).toInt(), minY, maxY)
      'R' -> binarySeat(code.drop(1), minX + ceil((maxX - minX) / 2.0).toInt(), maxX, minY, maxY)
      else -> throw IllegalStateException("Unknown seat $code")
    }
  }

  private fun seatId(seat: XY) = (seat.y * 8) + seat.x

}
import kotlin.math.abs
import kotlin.math.sign

object Day09 {

  fun part1(input: String) = simulateRope(input, 2)

  fun part2(input: String) = simulateRope(input, 10)

  private fun simulateRope(input: String, size: Int): Int {
    val rope = MutableList(size) { Point(0, 0) }
    val tailsVisited = mutableSetOf(rope.last())

    input.splitNewlines()
      .forEach { line ->
        val (direction, number) = line.splitWhitespace()
        repeat(number.toInt()) {
          rope[0] = rope[0].move(direction)
          rope.indices.drop(1).forEach { rope[it] = rope[it].follow(rope[it - 1]) }
          tailsVisited.add(rope.last())
        }
      }

    return tailsVisited.size
  }

  private fun Point.move(direction: String): Point {
    return when (direction) {
      "L" -> Point(x - 1, y)
      "R" -> Point(x + 1, y)
      "U" -> Point(x, y - 1)
      "D" -> Point(x, y + 1)
      else -> throw IllegalArgumentException("Unknown direction: $direction")
    }
  }

  private fun Point.follow(heads: Point): Point {
    if (abs(this.x - heads.x) < 2 && abs(this.y - heads.y) < 2) {
      return this
    }

    return Point(
      this.x + (heads.x - this.x).sign,
      this.y + (heads.y - this.y).sign
    )
  }

  private data class Point(val x: Int, val y: Int)

}
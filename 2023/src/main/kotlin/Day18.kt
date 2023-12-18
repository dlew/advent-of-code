object Day18 {

  private enum class Direction { UP, DOWN, LEFT, RIGHT }

  private data class Dig(val direction: Direction, val number: Long)

  private sealed class Line {
    data class Vertical(val x: Long, val yRange: LongRange) : Line()
    data class Horizontal(val y: Long, val xRange: LongRange) : Line()
  }

  private data class Pos(val x: Long, val y: Long)

  fun part1(input: String) = dig(parsePartOne(input))

  fun part2(input: String) = dig(parsePartTwo(input))

  private fun dig(plan: List<Dig>): Long {
    val lines = mutableListOf<Line>()
    var minY = 0L
    var maxY = 0L
    var minX = 0L
    var maxX = 0L

    // Find all vertical lines & the bounds
    var curr = Pos(0, 0)
    plan.forEach { dig ->
      val next = curr.move(dig.direction, dig.number)

      when (dig.direction) {
        Direction.UP -> {
          lines.add(Line.Vertical(curr.x, next.y..curr.y))
          if (next.y < minY) {
            minY = next.y
          }
        }

        Direction.DOWN -> {
          lines.add(Line.Vertical(curr.x, curr.y..next.y))
          if (next.y > maxY) {
            maxY = next.y
          }
        }

        Direction.LEFT -> {
          lines.add(Line.Horizontal(curr.y, next.x..curr.x))
          if (next.x < minX) {
            minX = next.x
          }
        }

        Direction.RIGHT -> {
          lines.add(Line.Horizontal(curr.y, curr.x..next.x))
          if (next.x > maxX) {
            maxX = next.x
          }
        }
      }

      curr = next
    }

    check(curr == Pos(0, 0)) { "Dig plan must end at start" }

    lines.sortBy {
      when (it) {
        is Line.Vertical -> it.x.toDouble()
        is Line.Horizontal -> it.xRange.first + ((it.xRange.last - it.xRange.first) / 2.0) // Make sure horizontal lines are between vertical lines
      }
    }

    val horizontalLines = lines.filterIsInstance<Line.Horizontal>().sortedBy { it.y }.map { it.y }

    var total = 0L
    var y = minY + 1
    while (y < maxY) {
      val filtered = lines.filter {
        when (it) {
          is Line.Vertical -> y in it.yRange
          is Line.Horizontal -> y == it.y
        }
      }

      // Add up inside points (paying attention to corners)
      var amount = 0L
      var inside = false
      filtered.zipWithNext().forEach { (l1, l2) ->
        if ((l1 is Line.Horizontal && l2 is Line.Vertical && l1.y == l2.yRange.last)
          || (l1 is Line.Vertical && l2 is Line.Horizontal && l2.y == l1.yRange.last)
        ) {
          inside = !inside
        } else if (l1 is Line.Vertical && l2 is Line.Vertical) {
          if (y != l1.yRange.first && y != l1.yRange.last) {
            inside = !inside
          }

          if (inside) {
            amount += l2.x - l1.x - 1
          }
        }
      }

      // Skip ahead to the next different row
      if (filtered.firstOrNull { it is Line.Horizontal } != null) {
        total += amount
        y++
      } else {
        val nextY = horizontalLines.first { it > y }
        val diff = nextY - y
        total += amount * diff
        y = nextY
      }
    }

    val edges = lines.sumOf {
      when (it) {
        is Line.Horizontal -> it.xRange.last - it.xRange.first
        is Line.Vertical -> it.yRange.last - it.yRange.first
      }
    }

    return total + edges
  }

  private fun Pos.move(direction: Direction, amount: Long) = when (direction) {
    Direction.UP -> copy(y = y - amount)
    Direction.DOWN -> copy(y = y + amount)
    Direction.LEFT -> copy(x = x - amount)
    Direction.RIGHT -> copy(x = x + amount)
  }

  private fun parsePartOne(input: String): List<Dig> {
    val regex = Regex("([UDLR]) (\\d+) \\((#\\w{6})\\)")
    return input.splitNewlines().map {
      val result = regex.matchEntire(it)!!
      Dig(
        direction = when (val dir = result.groupValues[1]) {
          "U" -> Direction.UP
          "D" -> Direction.DOWN
          "L" -> Direction.LEFT
          "R" -> Direction.RIGHT
          else -> throw IllegalStateException("Unrecognized direction: $dir")
        },
        number = result.groupValues[2].toLong(),
      )
    }
  }

  private fun parsePartTwo(input: String): List<Dig> {
    val regex = Regex("[UDLR] \\d+ \\(#(\\w{5})([0-3])\\)")
    return input.splitNewlines().map {
      val result = regex.matchEntire(it)!!
      Dig(
        direction = when (val dir = result.groupValues[2]) {
          "0" -> Direction.RIGHT
          "1" -> Direction.DOWN
          "2" -> Direction.LEFT
          "3" -> Direction.UP
          else -> throw IllegalStateException("Unrecognized direction: $dir")
        },
        number = result.groupValues[1].toLong(radix = 16),
      )
    }
  }

}
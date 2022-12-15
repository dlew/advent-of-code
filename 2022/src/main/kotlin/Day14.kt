import kotlin.math.sign

object Day14 {

  fun part1(input: String): Int {
    val grid = parseInput(input)

    var count = 0
    while (true) {
      val settledSand = dropSand(grid)
      if (settledSand.y > grid.maxY) {
        break
      }
      grid.putSand(settledSand.x, settledSand.y)
      count++
    }
    return count
  }

  fun part2(input: String): Int {
    val grid = parseInput(input)

    var count = 0
    while (!grid.get(500, 0)) {
      val settledSand = dropSand(grid)
      grid.putSand(settledSand.x, settledSand.y)
      count++
    }
    return count
  }

  private fun dropSand(grid: Grid): Point {
    var sandX = 500
    var sandY = 0

    while (true) {
      val sandXDiff = listOf(0, -1, 1).firstOrNull { !grid.get(sandX + it, sandY + 1) }
      if (sandXDiff == null) {
        return Point(sandX, sandY)
      } else {
        sandY++
        sandX += sandXDiff
      }
    }
  }

  private fun parseInput(input: String): Grid {
    val grid = Grid()
    input
      .splitNewlines()
      .map(::parseLine)
      .forEach { line ->
        line.windowed(2).forEach { (start, end) ->
          var x = start.x
          var y = start.y
          grid.putWall(x, y)

          while (x != end.x || y != end.y) {
            x += (end.x - x).sign
            y += (end.y - y).sign
            grid.putWall(x, y)
          }
        }
      }
    return grid
  }

  private fun parseLine(line: String): List<Point> {
    return line
      .split(" -> ")
      .map(String::splitCommas)
      .map { (x, y) -> Point(x.toInt(), y.toInt()) }
  }

  private data class Point(val x: Int, val y: Int)

  private class Grid {
    private val data = mutableSetOf<Point>()

    var maxY: Int = 0
      private set

    fun get(x: Int, y: Int) = (y == maxY + 2) || data.contains(Point(x, y))

    fun putWall(x: Int, y: Int) {
      data.add(Point(x, y))
      maxY = maxOf(maxY, y)
    }

    fun putSand(x: Int, y: Int) {
      data.add(Point(x, y))
    }
  }

}
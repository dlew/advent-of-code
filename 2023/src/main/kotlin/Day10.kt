private typealias Grid = List<String>

object Day10 {

  private data class Pos(val x: Int, val y: Int)

  private enum class State { OUTSIDE, PIPE, UNKNOWN }

  fun part1(input: String) = findLoop(input.splitNewlines()).size / 2

  fun part2(input: String): Int {
    val grid = input.splitNewlines()
    val loop = findLoop(grid)

    // Expand each tile to a 3x3 grid
    val expandedGrid = (0..<grid.size * 3).map { Array(grid[0].length * 3) { State.UNKNOWN } }

    // Draw the pipes (connecting the new expanded spots between)
    (loop + listOf(loop.first())).zipWithNext().forEach { (start, end) ->
      val expandedY = 1 + start.y * 3
      val expandedX = 1 + start.x * 3

      expandedGrid[expandedY][expandedX] = State.PIPE
      when (end) {
        start.copy(y = start.y - 1) -> {
          expandedGrid[expandedY - 1][expandedX] = State.PIPE
          expandedGrid[expandedY - 2][expandedX] = State.PIPE
        }

        start.copy(x = start.x + 1) -> {
          expandedGrid[expandedY][expandedX + 1] = State.PIPE
          expandedGrid[expandedY][expandedX + 2] = State.PIPE
        }

        start.copy(y = start.y + 1) -> {
          expandedGrid[expandedY + 1][expandedX] = State.PIPE
          expandedGrid[expandedY + 2][expandedX] = State.PIPE
        }

        start.copy(x = start.x - 1) -> {
          expandedGrid[expandedY][expandedX - 1] = State.PIPE
          expandedGrid[expandedY][expandedX - 2] = State.PIPE
        }
      }
    }

    // Flood fill the outside tiles on the expanded grid
    val queue = mutableListOf(Pos(0, 0))
    val marked = queue.toMutableSet()
    val yRange = expandedGrid.indices
    val xRange = expandedGrid[0].indices
    while (queue.isNotEmpty()) {
      val currPos = queue.removeAt(0)

      expandedGrid[currPos.y][currPos.x] = State.OUTSIDE

      val explore = listOf(
        currPos.copy(y = currPos.y - 1),
        currPos.copy(x = currPos.x + 1),
        currPos.copy(y = currPos.y + 1),
        currPos.copy(x = currPos.x - 1)
      ).filter {
        it !in marked
            && it.x in xRange
            && it.y in yRange
            && expandedGrid[it.y][it.x] != State.PIPE
      }

      marked.addAll(explore)
      queue.addAll(explore)
    }

    // Count key grid spaces for insiders
    return yRange.step(3).sumOf { y ->
      xRange.step(3).count { x ->
        expandedGrid[y + 1][x + 1] == State.UNKNOWN
      }
    }
  }

  private fun findLoop(grid: Grid): List<Pos> {
    val start = findStart(grid)
    val loop = mutableListOf<Pos>()
    val marked = mutableSetOf<Pos>() // Not necessary, but much faster using a Set here
    var curr: Pos? = start
    while (curr != null) {
      loop.add(curr)
      marked.add(curr)
      curr = directions
        .mapNotNull { it(grid, curr!!) }
        .firstOrNull { it !in marked }
    }
    return loop
  }

  private fun findStart(grid: Grid): Pos {
    grid.forEachIndexed { y, row ->
      val x = row.indexOf('S')
      if (x != -1) {
        return Pos(x, y)
      }
    }
    throw IllegalArgumentException("No starting position!")
  }

  private val directions = listOf(this::north, this::east, this::south, this::west)
  private val canGoNorth = setOf('S', '|', 'L', 'J')
  private val canGoEast = setOf('S', '-', 'L', 'F')
  private val canGoSouth = setOf('S', '|', '7', 'F')
  private val canGoWest = setOf('S', '-', 'J', '7')

  private fun north(grid: Grid, pos: Pos): Pos? {
    if (pos.y == 0) return null
    return testDirection(grid, pos, pos.copy(y = pos.y - 1), canGoNorth, canGoSouth)
  }

  private fun east(grid: Grid, pos: Pos): Pos? {
    if (pos.x == grid[0].length - 1) return null
    return testDirection(grid, pos, pos.copy(x = pos.x + 1), canGoEast, canGoWest)
  }

  private fun south(grid: Grid, pos: Pos): Pos? {
    if (pos.y == grid.size - 1) return null
    return testDirection(grid, pos, pos.copy(y = pos.y + 1), canGoSouth, canGoNorth)
  }

  private fun west(grid: Grid, pos: Pos): Pos? {
    if (pos.x == 0) return null
    return testDirection(grid, pos, pos.copy(x = pos.x - 1), canGoWest, canGoEast)
  }

  private fun testDirection(grid: Grid, pos: Pos, testPos: Pos, currAllowed: Set<Char>, testAllowed: Set<Char>): Pos? {
    val curr = grid[pos.y][pos.x]
    val test = grid[testPos.y][testPos.x]
    return if (curr in currAllowed && test in testAllowed) testPos else null
  }
}
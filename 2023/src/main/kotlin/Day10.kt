private typealias Grid = List<String>

object Day10 {

  private data class Pos(val x: Int, val y: Int)

  fun part1(input: String) = findLoop(input.splitNewlines()).size / 2

  fun part2(input: String): Int {
    val grid = input.splitNewlines()
    val loop = findLoop(grid).toSet()

    // Every time we hit a wall, switch parity, only count those inside
    // Kind of tricksy because we can't count all corners for parity checking
    var total = 0
    var parity = false
    grid.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (Pos(x, y) in loop) {
          if (c == '|' || c == 'L' || c == 'J') {
            parity = !parity
          }
        }
        else if (parity) {
          total++
        }
      }
    }

    return total
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
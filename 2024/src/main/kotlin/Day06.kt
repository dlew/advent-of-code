import models.Direction
import models.Position
import models.XY
import models.getXY

object Day06 {
  fun part1(input: String): Int {
    val (grid, start) = parse(input)
    return watchGuardSimplePath(grid, start).size
  }

  fun part2(input: String): Int {
    val (grid, start) = parse(input)
    val obstacles = watchGuardSimplePath(grid, start) - start.xy
    return obstacles
      .parallelStream()
      .filter { watchGuard(grid, start, it).loops }
      .count()
      .toInt()
  }

  private data class GuardPath(val visited: Set<Position>, val loops: Boolean)

  private fun watchGuard(grid: Array<CharArray>, start: Position, extraObstacle: XY): GuardPath {
    val visited = mutableSetOf<Position>()
    var curr = start
    do {
      visited.add(curr)
      val nextXY = curr.xy.move(curr.direction)
      val tile = grid.getXY(nextXY)
      curr = when {
        nextXY == extraObstacle || tile == '#' -> curr.copy(direction = curr.direction.rotate())
        tile == '.' -> curr.copy(xy = nextXY)
        else -> return GuardPath(visited, false)
      }
    } while (curr !in visited)
    return GuardPath(visited, true)
  }

  private fun watchGuardSimplePath(grid: Array<CharArray>, start: Position) =
    watchGuard(grid, start, XY(-1, -1)).visited.map { it.xy }.toSet()

  private fun Direction.rotate() = Direction.entries[(this.ordinal + 2) % Direction.entries.size]

  private fun parse(input: String): Pair<Array<CharArray>, Position> {
    val grid = input.replace('^', '.').splitNewlines().map { it.toCharArray() }.toTypedArray()
    val start = input.indexOf('^')
    val rowLength = grid[0].size + 1
    val startPosition = Position(XY(start % rowLength, start / rowLength), Direction.N)
    return grid to startPosition
  }
}
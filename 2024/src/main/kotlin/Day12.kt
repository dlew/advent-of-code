import models.Bounds
import models.CARDINAL_DIRECTIONS
import models.XY
import models.getXY

object Day12 {
  fun part1(input: String) = findAllFencedAreas(input).sumOf { it.area.size * it.perimeter }

  fun part2(input: String) = findAllFencedAreas(input).sumOf { it.area.size * it.numSides() }

  private data class FencedArea(val area: Set<XY>, val perimeter: Int)

  private fun findAllFencedAreas(input: String): List<FencedArea> {
    val grid = input.splitNewlines().map { it.toCharArray() }.toTypedArray()
    val visited = mutableSetOf<XY>()
    val fencedAreas = mutableListOf<FencedArea>()
    grid.forEachIndexed { y, row ->
      row.indices.forEach { x ->
        val xy = XY(x, y)
        if (xy !in visited) {
          val fencedArea = findFencedArea(grid, xy)
          fencedAreas.add(fencedArea)
          visited.addAll(fencedArea.area)
        }
      }
    }
    return fencedAreas
  }

  private fun findFencedArea(grid: Array<CharArray>, start: XY): FencedArea {
    val char = grid.getXY(start)
    val area = mutableSetOf<XY>()
    val queue = ArrayDeque(listOf(start))
    var perimeter = 0
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr in area) continue
      area.add(curr)
      CARDINAL_DIRECTIONS.forEach { direction ->
        val next = curr.move(direction)
        if (grid.getXY(next) == char) queue.add(next)
        else perimeter++
      }
    }
    return FencedArea(area, perimeter)
  }

  private fun FencedArea.numSides(): Int {
    val area = this.area
    // Extra bounds to make it easier to find the first/last wall
    val bounds = Bounds(
      xRange = area.minOf { it.x } - 1..area.maxOf { it.x } + 1,
      yRange = area.minOf { it.y } - 1..area.maxOf { it.y } + 1
    )
    return numSidesOneDirection(area, bounds.yRange, bounds.xRange, true) +
        numSidesOneDirection(area, bounds.xRange, bounds.yRange, false)
  }

  // Scans every row (or column) for when we switch from inside/outside to figure out how many sides there are
  private fun numSidesOneDirection(area: Set<XY>, aRange: IntRange, bRange: IntRange, horizontal: Boolean): Int {
    data class Wall(val loc: Int, val inside: Boolean)

    var numSides = 0
    var lastWalls = emptySet<Wall>()
    aRange.forEach { a ->
      val walls = mutableSetOf<Wall>()
      var inside = false
      bRange.forEach { b ->
        val xy = if (horizontal) XY(b, a) else XY(a, b)
        if (inside != xy in area) {
          inside = !inside
          walls.add((Wall(b, inside)))
        }
      }

      // The trick: any differences here represents a change in wall location
      numSides += (walls - lastWalls).size

      lastWalls = walls
    }
    return numSides
  }
}
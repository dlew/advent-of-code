import models.Bounds
import models.XY
import models.inBounds

object Day08 {
  fun part1(input: String) = countAntiNodes(input, false)

  fun part2(input: String) = countAntiNodes(input, true)

  private fun countAntiNodes(input: String, resonantHarmonics: Boolean): Int {
    val (bounds, nodes) = parse(input)
    return nodes.values
      .flatMap { list ->
        list.flatMapIndexed { index, a ->
          list.drop(index + 1).flatMap { b ->
            if (resonantHarmonics) resonantAntiNodes(a, b, bounds) else antiNodes(a, b)
          }
        }
      }
      .distinct()
      .filter { it.inBounds(bounds) }
      .size
  }

  private fun antiNodes(a: XY, b: XY): List<XY> {
    val dx = a.x - b.x
    val dy = a.y - b.y
    return listOf(
      a.copy(x = a.x + dx, y = a.y + dy),
      b.copy(x = b.x - dx, y = b.y - dy)
    )
  }

  private fun resonantAntiNodes(a: XY, b: XY, bounds: Bounds): List<XY> {
    val dx = a.x - b.x
    val dy = a.y - b.y
    return antiNodesUntilBounds(a, dx, dy, bounds) + antiNodesUntilBounds(b, -dx, -dy, bounds)
  }

  private fun antiNodesUntilBounds(xy: XY, dx: Int, dy: Int, bounds: Bounds): List<XY> {
    val antiNodes = mutableListOf<XY>()
    var curr = xy
    while (curr.inBounds(bounds)) {
      antiNodes.add(curr)
      curr = curr.copy(x = curr.x + dx, y = curr.y + dy)
    }
    return antiNodes
  }

  private fun parse(input: String): Pair<Bounds, Map<Char, List<XY>>> {
    val grid = input.splitNewlines().map { it.toCharArray() }.toTypedArray()
    val nodes = mutableMapOf<Char, MutableList<XY>>()
    grid.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (c.isLetterOrDigit()) {
          nodes.getOrPut(c) { mutableListOf() }.add(XY(x, y))
        }
      }
    }
    return Bounds(grid[0].indices, grid.indices) to nodes
  }
}
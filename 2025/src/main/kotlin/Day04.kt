import models.XY
import models.getXY
import utils.splitNewlines

object Day04 {
  fun part1(input: String): Int {
    val grid = parse(input)
    return grid.withIndex().sumOf { (y, row) ->
      row.indices.count { x ->
        grid[y][x] == '@' && countRolls(grid, XY(x, y)) < 5
      }
    }
  }

  fun part2(input: String): Int {
    var grid = parse(input)
    var removed = 0
    while (true) {
      val newGrid = grid.map { it.clone() }.toTypedArray()
      grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          if (c == '@' && countRolls(grid, XY(x, y)) < 5) {
            newGrid[y][x] = '.'
            removed++
          }
        }
      }

      // If nothing changed, we're done
      if (grid.contentDeepEquals(newGrid)) return removed

      // Try again with newly updated grid
      grid = newGrid
    }
  }

  private fun countRolls(grid: Array<CharArray>, xy: XY): Int {
    return (-1..1).sumOf { dx ->
      (-1..1).count { dy ->
        grid.getXY(XY(x = xy.x + dx, y = xy.y + dy)) == '@'
      }
    }
  }

  private fun parse(input: String) = input.splitNewlines().map { it.toCharArray() }.toTypedArray()
}
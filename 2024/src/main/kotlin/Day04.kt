import models.Direction
import models.XY
import models.getXY

object Day04 {
  fun part1(input: String): Int {
    val grid = input.toGrid()
    return grid.withIndex().sumOf { (y, row) ->
      row.indices.sumOf { x ->
        Direction.entries.count { direction ->
          detectLetters(grid, XY(x, y), direction, "XMAS")
        }
      }
    }
  }

  private fun detectLetters(grid: Array<CharArray>, xy: XY, direction: Direction, letters: String): Boolean {
    if (letters.isEmpty()) return true
    if (grid.getXY(xy) != letters.first()) return false
    return detectLetters(grid, xy.move(direction), direction, letters.drop(1))
  }

  // Find all letters "A" then check that the corner letters surrounding it would form an X-mas
  fun part2(input: String): Int {
    val grid = input.toGrid()
    return grid.withIndex().sumOf { (y, row) ->
      row.indices.count { x ->
        val center = XY(x, y)
        return@count grid.getXY(center) == 'A' && getCorners(grid, center) in VALID_CORNERS
      }
    }
  }

  private val VALID_CORNERS = setOf("MMSS", "MSSM", "SSMM", "SMMS")

  private fun getCorners(grid: Array<CharArray>, center: XY) = listOf(
    grid.getXY(center.move(Direction.NE)),
    grid.getXY(center.move(Direction.SE)),
    grid.getXY(center.move(Direction.SW)),
    grid.getXY(center.move(Direction.NW))
  ).joinToString("")

  private fun String.toGrid() = splitNewlines().map { it.toCharArray() }.toTypedArray()
}


import models.XY
import utils.splitNewlines

object Day03 {
  fun part1(input: String) = solve(parse(input), listOf(XY(3, 1)))

  fun part2(input: String) = solve(parse(input), listOf(XY(1, 1), XY(3, 1), XY(5, 1), XY(7, 1), XY(1, 2)))

  private fun solve(grid: Array<BooleanArray>, slopes: List<XY>) = slopes
    .map { countTrees(grid, it) }
    .reduce(Long::times)

  private fun countTrees(grid: Array<BooleanArray>, slope: XY): Long {
    val width = grid[0].size
    var pos = XY(0, 0)
    var count = 0L
    while (pos.y < grid.size) {
      if (grid[pos.y][pos.x % width]) {
        count++
      }
      pos = pos.copy(x = pos.x + slope.x, y = pos.y + slope.y)
    }
    return count
  }

  private fun parse(input: String) = input.splitNewlines()
    .map { line -> line.map { it != '.' }.toBooleanArray() }
    .toTypedArray()

}
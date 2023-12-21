import java.util.*

object Day21 {

  fun part1(input: String, steps: Int): Long {
    check(steps % 2 == 0) { "Number of steps must be even" }
    val (grid, start) = parse(input)
    return explore(grid, start, steps).even
  }

  fun part2(input: String, steps: Int): Long {
    val (grid, start) = parse(input)

    // This algorithm relies on certain properties of the input; define them via checks
    check(steps % 2 == 1) { "Number of steps must be odd" }
    check(grid.size == grid[0].length) { "Input grid must be square" }
    check(grid.size % 2 == 1) { "Grid size must be odd" }
    check(start.x == grid.size / 2 && start.y == grid.size / 2) { "Start must be in center of square" }
    check((start.x + start.y) % 2 == 0) { "Start must be an even position" }
    check(steps % grid.size == grid.size / 2) { "Num steps allows one to go straight to the center of another square" }
    check(grid.first().all { it != '#' }) { "Horizontal top line must be clear of rocks" }
    check(grid[grid.size / 2].all { it != '#' }) { "Horizontal midline must be clear of rocks" }
    check(grid.last().all { it != '#' }) { "Horizontal bottom line must be clear of rocks" }
    check(grid.all { it[grid.size / 2] != '#' }) { "Vertical midline must be clear of rocks" }
    check(grid.all { it.first() != '#' }) { "Vertical left line must be clear of rocks" }
    check(grid.all { it.last() != '#' }) { "Vertical right line must be clear of rocks" }
    // FYI, there must be a clear diamond shape inside as well, no easy way to check though

    // Pre-calculate all the possible grids (named on where you start)
    val tipSteps = grid.size - 1
    val smallCornerSteps = grid.size / 2
    val largeCornerSteps = (grid.size - 1) + (grid.size / 2)
    val filled = explore(grid, Pos(start.x, 130), Int.MAX_VALUE)
    val north = explore(grid, Pos(start.x, 0), tipSteps)
    val northEastSmall = explore(grid, Pos(grid.size - 1, 0), smallCornerSteps)
    val northEastLarge = explore(grid, Pos(grid.size - 1, 0), largeCornerSteps)
    val east = explore(grid, Pos(grid.size - 1, start.y), tipSteps)
    val southEastSmall = explore(grid, Pos(grid.size - 1, grid.size - 1), smallCornerSteps)
    val southEastLarge = explore(grid, Pos(grid.size - 1, grid.size - 1), largeCornerSteps)
    val south = explore(grid, Pos(start.x, grid.size - 1), tipSteps)
    val southWestSmall = explore(grid, Pos(0, grid.size - 1), smallCornerSteps)
    val southWestLarge = explore(grid, Pos(0, grid.size - 1), largeCornerSteps)
    val west = explore(grid, Pos(0, start.y), tipSteps)
    val northWestSmall = explore(grid, Pos(0, 0), smallCornerSteps)
    val northWestLarge = explore(grid, Pos(0, 0), largeCornerSteps)

    // Calculate number of each type of grid based on number of grids traveled
    val numGridsTraveled = (steps / grid.size).toLong()

    // One of each tip
    val tips = north.odd + south.odd + west.odd + east.odd

    // Small corners
    val smallCorners =
      numGridsTraveled * (northEastSmall.even + southEastSmall.even + southWestSmall.even + northWestSmall.even)

    // Large corners
    val largeCorners =
      (numGridsTraveled - 1) * (northEastLarge.odd + southEastLarge.odd + southWestLarge.odd + northWestLarge.odd)

    // Number of odd and even filled grids
    val numOddFilledGrids = (numGridsTraveled - 1) * (numGridsTraveled - 1)
    val numEvenFilledGrids = numGridsTraveled * numGridsTraveled

    // Sum it all up
    return (numOddFilledGrids * filled.odd) + (numEvenFilledGrids * filled.even) + tips + smallCorners + largeCorners
  }

  private fun explore(grid: List<String>, start: Pos, steps: Int): Stats {
    val queue = ArrayDeque<Step>()
    queue.add(Step(start, steps))
    val visited = HashSet<Pos>()

    while (queue.isNotEmpty()) {
      val (pos, stepsRemaining) = queue.poll()

      if (pos in visited) {
        continue
      }

      visited.add(pos)

      if (stepsRemaining == 0) {
        continue
      }

      queue.addAll(
        Direction.entries
          .map { pos.move(it) }
          .filter { it !in visited && it.x in grid[0].indices && it.y in grid.indices && grid[it.y][it.x] != '#' }
          .map { Step(it, stepsRemaining - 1) }
      )
    }

    val oddCount = visited.count { it.isOdd() }.toLong()
    return Stats(odd = oddCount, even = visited.size - oddCount)
  }

  private data class Pos(val x: Int, val y: Int) {
    fun move(direction: Direction) = when (direction) {
      Direction.NORTH -> copy(y = y - 1)
      Direction.EAST -> copy(x = x + 1)
      Direction.SOUTH -> copy(y = y + 1)
      Direction.WEST -> copy(x = x - 1)
    }

    // Note that this is relative based on the starting position of the walk
    fun isOdd() = (x + y) % 2 == 1
  }

  private enum class Direction { NORTH, EAST, SOUTH, WEST }

  private data class Step(val pos: Pos, val count: Int)

  private data class Stats(val even: Long, val odd: Long)

  private data class Config(val grid: List<String>, val start: Pos)

  private fun parse(input: String): Config {
    val grid = input.splitNewlines()
    val start = grid.indices.firstNotNullOf { y ->
      val x = grid[y].indexOf('S')
      if (x == -1) null else Pos(x, y)
    }
    return Config(grid, start)
  }
}
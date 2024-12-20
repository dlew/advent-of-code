import models.CARDINAL_DIRECTIONS
import models.XY
import models.getXY
import kotlin.math.abs

object Day20 {
  fun part1(input: String, cutoff: Int) = solve(input, cutoff, 2)

  fun part2(input: String, cutoff: Int) = solve(input, cutoff, 20)

  private fun solve(input: String, cutoff: Int, cheatTime: Int): Int {
    val racetrack = input.splitNewlines().map { it.toCharArray() }.toTypedArray()
    val start = findStart(racetrack)
    val numSteps = input.count { it == '.' } + 1 // There's only one path!

    // Find how many steps it takes to get to the end from each space
    val stepMap = createStepsRemainingGrid(racetrack, start, numSteps)

    // For each amount of cheating, find all diffs N spots away
    val diffMap = generateDiffMap(cheatTime)

    // For each spot, figure out where you could reach from there via cheating, and how much time it would save
    var counter = 0
    stepMap.forEachIndexed { y, row ->
      row.forEachIndexed { x, stepsRemaining ->
        if (stepsRemaining != Int.MAX_VALUE) {
          counter += (2..cheatTime).sumOf { cheatDistance ->
            val goal = stepsRemaining - cheatDistance - cutoff
            return@sumOf diffMap[cheatDistance]!!.count { (diffX, diffY) ->
              val remainder = stepMap.getXY(XY(x + diffX, y + diffY))
              return@count remainder != null && remainder <= goal
            }
          }
        }
      }
    }
    return counter
  }

  // Grid has a number for each blank spot in the racetrack that is how many more steps are remaining until the end
  private fun createStepsRemainingGrid(racetrack: Array<CharArray>, start: XY, numSteps: Int): Array<IntArray> {
    val stepsRemainingGrid = Array(racetrack.size) { IntArray(racetrack[0].size) { Int.MAX_VALUE } }
    var stepsRemaining = numSteps
    var last: XY? = null
    var xy = start
    while (true) {
      stepsRemainingGrid[xy.y][xy.x] = stepsRemaining--

      if (racetrack.getXY(xy) == 'E') return stepsRemainingGrid

      val next = CARDINAL_DIRECTIONS
        .map { xy.move(it) }
        .first { it != last && racetrack.getXY(it) != '#' }
      last = xy
      xy = next
    }
  }

  private fun generateDiffMap(cheatTime: Int): Map<Int, List<XY>> {
    return (2..cheatTime).associateWith { distance ->
      (-distance..distance).flatMap { diffX ->
        (-distance..distance).mapNotNull { diffY ->
          if (abs(diffX) + abs(diffY) == distance) XY(diffX, diffY) else null
        }
      }
    }
  }

  private fun findStart(racetrack: Array<CharArray>): XY {
    racetrack.forEachIndexed { y, row ->
      val x = row.indexOf('S')
      if (x != -1) return XY(x, y)
    }
    throw IllegalArgumentException("No start!")
  }
}
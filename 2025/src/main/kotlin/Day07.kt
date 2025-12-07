import models.XY
import utils.splitNewlines

object Day07 {
  fun part1(input: String): Int {
    val manifold = parse(input)
    val beams = mutableListOf(getStart(manifold))
    var splitCount = 0
    val visited = mutableSetOf<XY>()
    while (beams.isNotEmpty()) {
      val beam = beams.removeFirst()

      // If we've already traced this beam path OR the beam's hit the bottom, ignore
      if (beam in visited || beam.y == manifold.size - 1) continue
      visited.add(beam)

      if (manifold[beam.y][beam.x] == '.') {
        beams.add(beam.copy(y = beam.y + 1))
      } else {
        beams.add(beam.copy(x = beam.x - 1))
        beams.add(beam.copy(x = beam.x + 1))
        splitCount++
      }
    }

    return splitCount
  }

  fun part2(input: String): Long {
    val manifold = parse(input)
    val memo = mutableMapOf<XY, Long>()

    fun countTimelines(beam: XY): Long {
      if (beam.y == manifold.size - 1) return 1

      return memo.getOrPut(beam) {
        if (manifold[beam.y][beam.x] == '.') {
          return@getOrPut countTimelines(beam.copy(y = beam.y + 1))
        } else {
          val left = countTimelines(beam.copy(x = beam.x - 1))
          val right = countTimelines(beam.copy(x = beam.x + 1))
          return@getOrPut left + right
        }
      }
    }

    return countTimelines(getStart(manifold))
  }

  private fun parse(input: String) = input.splitNewlines().map { it.toCharArray() }.toTypedArray()

  private fun getStart(manifold: Array<CharArray>) = XY(x = manifold.first().indexOf('S'), y = 1)
}
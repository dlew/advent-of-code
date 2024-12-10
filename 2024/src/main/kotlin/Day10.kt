import models.CARDINAL_DIRECTIONS
import models.XY
import models.getXY

object Day10 {
  fun part1(input: String) = countTrails(input, false)

  fun part2(input: String) = countTrails(input, true)

  private fun countTrails(input: String, distinctPaths: Boolean): Int {
    val map = input.splitNewlines().map { line -> line.toIntList() }
    return map.withIndex().sumOf { (y, row) ->
      row.indices
        .map { x -> XY(x, y) }
        .filter { map.getXY(it) == 0 }
        .sumOf {
          val trailEndings = findTrailEndings(map, it)
          if (distinctPaths) trailEndings.size else trailEndings.toSet().size
        }
    }
  }

  private fun findTrailEndings(map: List<List<Int>>, pos: XY): List<XY> {
    val height = map.getXY(pos)!!
    if (height == 9) return listOf(pos)

    return CARDINAL_DIRECTIONS
      .map { pos.move(it) }
      .filter { map.getXY(it) == height + 1 }
      .flatMap { findTrailEndings(map, it) }
  }
}
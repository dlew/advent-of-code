import kotlin.math.abs

object Day15 {

  fun part1(input: String, y: Int): Int {
    val sensors = parseInput(input)

    val startX = sensors.first().closestBeacon.x

    var left = startX - 1
    while (sensors.any { it.rulesOut(left, y) }) {
      left--
    }

    var right = startX + 1
    while (sensors.any { it.rulesOut(right, y) }) {
      right++
    }

    return right - left - 2
  }

  fun part2(input: String, max: Int): Long {
    val sensors = parseInput(input)

    (0..max).forEach { y ->
      var x = 0
      while (x <= max) {
        val maxSize = sensors.maxOf { it.distanceToBeacon - manhattanDistance(it.location, x, y) }
        if (maxSize == -1) {
          return tuningFrequency(x, y)
        }
        x += maxSize + 1
      }
    }

    throw IllegalStateException("Could not find beacon!")
  }

  private fun manhattanDistance(p1: Point, p2: Point) = manhattanDistance(p1, p2.x, p2.y)

  private fun manhattanDistance(p: Point, x: Int, y: Int) = abs(p.x - x) + abs(p.y - y)

  private fun tuningFrequency(x: Int, y: Int) = x * 4_000_000L + y

  private data class Point(val x: Int, val y: Int)

  private data class Sensor(val location: Point, val closestBeacon: Point) {
    val distanceToBeacon = manhattanDistance(location, closestBeacon)

    inline fun rulesOut(x: Int, y: Int) = manhattanDistance(location, x, y) <= distanceToBeacon
  }

  private val INPUT_REGEX = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

  private fun parseInput(input: String): List<Sensor> {
    return input
      .splitNewlines()
      .map {
        val (sensorX, sensorY, beaconX, beaconY) = INPUT_REGEX.matchEntire(it)!!.destructured
        Sensor(
          location = Point(sensorX.toInt(), sensorY.toInt()),
          closestBeacon = Point(beaconX.toInt(), beaconY.toInt())
        )
      }
  }

}
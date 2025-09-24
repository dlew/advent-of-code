import models.CardinalDirection
import models.XY
import utils.splitNewlines
import kotlin.math.absoluteValue

object Day12 {
  fun part1(input: String): Int {
    var pos = XY(0, 0)
    var facing = CardinalDirection.EAST

    input.splitNewlines().forEach {
      val command = it[0]
      val value = it.drop(1).toInt()
      when (command) {
        'N' -> pos = pos.move(CardinalDirection.NORTH, value)
        'E' -> pos = pos.move(CardinalDirection.EAST, value)
        'S' -> pos = pos.move(CardinalDirection.SOUTH, value)
        'W' -> pos = pos.move(CardinalDirection.WEST, value)
        'F' -> pos = pos.move(facing, value)
        'R' -> facing = turnDirectionClockwise(facing, value)
        'L' -> facing = turnDirectionClockwise(facing, convertCounterClockwiseToClockwise(value))
        else -> throw IllegalArgumentException("Unknown command: $command")
      }
    }

    return pos.manhattanDistance()
  }

  fun part2(input: String): Int {
    var pos = XY(0, 0)
    var waypoint = XY(10, -1)
    input.splitNewlines().forEach {
      val command = it[0]
      val value = it.drop(1).toInt()
      when (command) {
        'N' -> waypoint = waypoint.move(CardinalDirection.NORTH, value)
        'E' -> waypoint = waypoint.move(CardinalDirection.EAST, value)
        'S' -> waypoint = waypoint.move(CardinalDirection.SOUTH, value)
        'W' -> waypoint = waypoint.move(CardinalDirection.WEST, value)
        'F' -> pos = XY(x = pos.x + (waypoint.x * value), y = pos.y + (waypoint.y * value))
        'R' -> waypoint = turnWaypointClockwise(waypoint, value)
        'L' -> waypoint = turnWaypointClockwise(waypoint, convertCounterClockwiseToClockwise(value))
        else -> throw IllegalArgumentException("Unknown command: $command")
      }
    }

    return pos.manhattanDistance()
  }

  private fun XY.move(direction: CardinalDirection, distance: Int): XY {
    return when (direction) {
      CardinalDirection.NORTH -> copy(y = y - distance)
      CardinalDirection.EAST -> copy(x = x + distance)
      CardinalDirection.SOUTH -> copy(y = y + distance)
      CardinalDirection.WEST -> copy(x = x - distance)
    }
  }

  private fun turnDirectionClockwise(direction: CardinalDirection, degrees: Int): CardinalDirection {
    var newDirection = direction
    repeat(degrees / 90) {
      newDirection = when (newDirection) {
        CardinalDirection.NORTH -> CardinalDirection.EAST
        CardinalDirection.EAST -> CardinalDirection.SOUTH
        CardinalDirection.SOUTH -> CardinalDirection.WEST
        CardinalDirection.WEST -> CardinalDirection.NORTH
      }
    }
    return newDirection
  }

  private fun turnWaypointClockwise(waypoint: XY, degrees: Int): XY {
    var newWaypoint = waypoint
    repeat(degrees / 90) {
      newWaypoint = XY(-newWaypoint.y, newWaypoint.x)
    }
    return newWaypoint
  }

  private fun convertCounterClockwiseToClockwise(degrees: Int) = if (degrees == 180) 180 else (degrees + 180) % 360

  private fun XY.manhattanDistance() = x.absoluteValue + y.absoluteValue
}
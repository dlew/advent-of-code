import Day23.Direction.*
import java.util.*

object Day23 {

  fun part1(input: String): Int {
    var elves = parseElves(input)
    repeat(10) {
      elves = round(elves, Direction.values()[it % 4])
    }

    val minX = elves.minOf { it.x }
    val maxX = elves.maxOf { it.x }
    val minY = elves.minOf { it.y }
    val maxY = elves.maxOf { it.y }
    return (maxX - minX + 1) * (maxY - minY + 1) - elves.size
  }

  fun part2(input: String): Int {
    var elves = parseElves(input)
    var round = 0
    while (true) {
      val nextElves = round(elves, Direction.values()[round % 4])

      if (elves == nextElves) {
        return round + 1
      }

      elves = nextElves
      round++
    }
  }

  private fun round(elves: Set<Point>, startingDirection: Direction): Set<Point> {
    val proposedMoves = mutableMapOf<Point, MutableSet<Point>>()
    val directionOrder = directionOrder(startingDirection)
    val nextPositions = mutableSetOf<Point>()

    // Propose moves
    for (elf in elves) {
      // If there's no adjacent elves, don't move
      val adjacentPoints = adjacentPoints(elf)
      if (adjacentPoints.none { it in elves }) {
        nextPositions.add(elf)
        continue
      }

      var foundMove = false
      for (direction in directionOrder) {
        val check = checkDirection(elf, direction)
        if (check.none { it in elves }) {
          val move = moveDirection(elf, direction)
          proposedMoves.getOrPut(move) { mutableSetOf() }.add(elf)
          foundMove = true
          break
        }
      }

      // No directions to move - just stay put
      if (!foundMove) {
        nextPositions.add(elf)
      }
    }

    // Execute moves
    for ((proposedMove, fromElves) in proposedMoves.entries) {
      if (fromElves.size > 1) {
        nextPositions.addAll(fromElves)
      } else {
        nextPositions.add(proposedMove)
      }
    }

    return nextPositions
  }

  private fun directionOrder(startingDirection: Direction): List<Direction> {
    val directions = Direction.values().toMutableList()
    Collections.rotate(directions, -startingDirection.ordinal)
    return directions
  }

  private fun adjacentPoints(point: Point): List<Point> {
    return (-1..1).flatMap { xMod ->
      (-1..1).map { yMod ->
        Point(point.x + xMod, point.y + yMod)
      }
    }.filter { it != point }
  }

  private fun checkDirection(point: Point, direction: Direction): Set<Point> {
    val (x, y) = point
    return when (direction) {
      NORTH -> setOf(Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1))
      SOUTH -> setOf(Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1))
      WEST -> setOf(Point(x - 1, y - 1), Point(x - 1, y), Point(x - 1, y + 1))
      EAST -> setOf(Point(x + 1, y - 1), Point(x + 1, y), Point(x + 1, y + 1))
    }
  }

  private fun moveDirection(point: Point, direction: Direction): Point {
    val (x, y) = point
    return when (direction) {
      NORTH -> Point(x, y - 1)
      SOUTH -> Point(x, y + 1)
      WEST -> Point(x - 1, y)
      EAST -> Point(x + 1, y)
    }
  }

  private enum class Direction { NORTH, SOUTH, WEST, EAST }

  private data class Point(val x: Int, val y: Int)

  private fun parseElves(input: String): Set<Point> {
    val elves = mutableSetOf<Point>()
    input.splitNewlines().forEachIndexed { y, s ->
      s.forEachIndexed { x, c ->
        if (c == '#') {
          elves.add(Point(x, y))
        }
      }
    }
    return elves
  }

}
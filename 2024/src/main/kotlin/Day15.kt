import models.Direction
import models.XY
import models.getXY

private typealias Warehouse = Array<CharArray>

object Day15 {
  fun part1(input: String): Long {
    val scenario = parse(input, false)
    val warehouse = scenario.warehouse
    var robot = scenario.robot

    scenario.movements.forEach { direction ->
      val nextOpening = nextOpening(warehouse, robot, direction)
      if (nextOpening != null) {
        robot = robot.move(direction)
        if (robot != nextOpening) {
          warehouse[robot.y][robot.x] = '.'
          warehouse[nextOpening.y][nextOpening.x] = 'O'
        }
      }
    }

    return gpsSum(warehouse)
  }

  fun part2(input: String): Long {
    val scenario = parse(input, true)
    var warehouse = scenario.warehouse
    var robot = scenario.robot

    scenario.movements.forEach { direction ->
      val backup = warehouse.map { it.clone() }.toTypedArray()
      if (push(warehouse, robot, direction)) {
        robot = robot.move(direction)
      } else {
        warehouse = backup
      }
    }

    return gpsSum(warehouse)
  }

  private fun nextOpening(warehouse: Warehouse, robot: XY, direction: Direction): XY? {
    var xy = robot.move(direction)
    while (true) {
      val curr = warehouse.getXY(xy)
      if (curr == null || curr == '#') return null
      if (curr == '.') return xy
      xy = xy.move(direction)
    }
  }

  private fun gpsSum(warehouse: Warehouse): Long {
    var sum = 0L
    warehouse.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (c == 'O' || c == '[') {
          sum += 100 * y + x
        }
      }
    }
    return sum
  }

  // Pushes things in the warehouse, but returns false if it failed to push
  private fun push(warehouse: Warehouse, start: XY, direction: Direction): Boolean {
    data class Pusher(val pos: XY, val carrying: Char)

    val vertical = direction == Direction.N || direction == Direction.S
    var pushers = mutableListOf(Pusher(start, warehouse.getXY(start)!!))
    warehouse[start.y][start.x] = '.'

    // Push one layer at a time (though the current layer may get another pusher added to it, if we push a box)
    // until we have no more boxes that need pushing
    while (pushers.isNotEmpty()) {
      val nextPushers = mutableListOf<Pusher>()
      var index = 0
      val visited = mutableSetOf<XY>()
      while (index < pushers.size) {
        val pusher = pushers[index++]

        if (pusher.pos in visited) continue
        visited.add(pusher.pos)

        val nextPos = pusher.pos.move(direction)
        val nextVal = warehouse.getXY(nextPos)
        if (nextVal == '#' || nextVal == null) return false

        warehouse[nextPos.y][nextPos.x] = pusher.carrying

        if (nextVal != '.') {
          nextPushers.add(Pusher(nextPos, nextVal))

          if (vertical) {
            if (nextVal == '[') {
              pushers.add(Pusher(pusher.pos.copy(x = nextPos.x + 1), '.'))
            } else if (nextVal == ']') {
              pushers.add(Pusher(pusher.pos.copy(x = nextPos.x - 1), '.'))
            }
          }
        }
      }
      pushers = nextPushers
    }

    return true
  }

  private class Scenario(val warehouse: Warehouse, val robot: XY, val movements: List<Direction>)

  private fun parse(input: String, embiggen: Boolean): Scenario {
    val (rawWarehouse, rawInstructions) = input.split("\n\n")
    var warehouse = rawWarehouse.splitNewlines().map { it.toCharArray() }.toTypedArray()
    if (embiggen) {
      warehouse = warehouse.embiggen()
    }
    var robot: XY? = null
    warehouse.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (c == '@') {
          robot = XY(x, y)
          warehouse[y][x] = '.'
        }
      }
    }
    val movements = rawInstructions.replace("\n", "")
      .map {
        when (it) {
          '^' -> Direction.N
          '>' -> Direction.E
          'v' -> Direction.S
          '<' -> Direction.W
          else -> throw IllegalArgumentException()
        }
      }
    return Scenario(warehouse, robot!!, movements)
  }

  private fun Warehouse.embiggen(): Warehouse {
    return map { row ->
      row.joinToString("") { c ->
        when (c) {
          '#' -> "##"
          'O' -> "[]"
          '.' -> ".."
          '@' -> "@."
          else -> throw RuntimeException()
        }
      }.toCharArray()
    }.toTypedArray()
  }
}
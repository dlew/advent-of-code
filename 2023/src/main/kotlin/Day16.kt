import java.util.concurrent.Callable
import java.util.concurrent.Executors

object Day16 {

  private enum class Direction { NORTH, EAST, SOUTH, WEST }

  private data class Pos(val x: Int, val y: Int)

  private data class Beam(val direction: Direction, val pos: Pos)

  fun part1(input: String): Int {
    val layout = input.splitNewlines().toTypedArray()
    return numEnergized(layout, Beam(Direction.EAST, Pos(0, 0)))
  }

  fun part2(input: String): Int {
    val layout = input.splitNewlines().toTypedArray()
    val starts = layout.indices.map { y -> Beam(Direction.EAST, Pos(0, y)) } +
        layout.indices.map { y -> Beam(Direction.WEST, Pos(layout[y].length - 1, y)) } +
        layout[0].indices.map { x -> Beam(Direction.SOUTH, Pos(x, 0)) } +
        layout[0].indices.map { x -> Beam(Direction.NORTH, Pos(x, layout.size - 1)) }
    val callables = starts.map { start -> Callable { numEnergized(layout, start) } }
    return Executors.newWorkStealingPool().invokeAll(callables).maxOf { it.get() }
  }

  // Optimization to avoid allocations
  private val northList = listOf(Direction.NORTH)
  private val eastList = listOf(Direction.EAST)
  private val southList = listOf(Direction.SOUTH)
  private val westList = listOf(Direction.WEST)
  private val northSouthList = listOf(Direction.NORTH, Direction.SOUTH)
  private val eastWestList = listOf(Direction.EAST, Direction.WEST)

  private fun numEnergized(layout: Array<String>, start: Beam): Int {
    val beams = mutableListOf(start)
    val energized = mutableSetOf<Pos>()
    val visited = mutableSetOf<Beam>()

    while (beams.isNotEmpty()) {
      val beam = beams.removeFirst()
      if (beam in visited) {
        continue
      }

      visited.add(beam)
      energized.add(beam.pos)

      val nextDirections = when (val tile = layout[beam.pos.y][beam.pos.x]) {
        '.' -> listOf(beam.direction)

        '/' -> when (beam.direction) {
          Direction.NORTH -> eastList
          Direction.EAST -> northList
          Direction.SOUTH -> westList
          Direction.WEST -> southList
        }

        '\\' -> when (beam.direction) {
          Direction.NORTH -> westList
          Direction.EAST -> southList
          Direction.SOUTH -> eastList
          Direction.WEST -> northList
        }

        '|' -> when (beam.direction) {
          Direction.EAST, Direction.WEST -> northSouthList
          else -> listOf(beam.direction)
        }

        '-' -> when (beam.direction) {
          Direction.NORTH, Direction.SOUTH -> eastWestList
          else -> listOf(beam.direction)
        }

        else -> throw IllegalArgumentException("Unknown tile: $tile")
      }

      beams.addAll(
        nextDirections
          .mapNotNull { nextDirection ->
            val nextPos = when (nextDirection) {
              Direction.NORTH -> beam.pos.copy(y = beam.pos.y - 1)
              Direction.EAST -> beam.pos.copy(x = beam.pos.x + 1)
              Direction.SOUTH -> beam.pos.copy(y = beam.pos.y + 1)
              Direction.WEST -> beam.pos.copy(x = beam.pos.x - 1)
            }
            if (nextPos.x in layout[0].indices && nextPos.y in layout.indices) Beam(nextDirection, nextPos) else null
          }
      )
    }

    return energized.size
  }
}
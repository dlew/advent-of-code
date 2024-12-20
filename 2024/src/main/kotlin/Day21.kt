import models.Direction
import models.XY
import models.getXY
import kotlin.math.abs

object Day21 {

  fun part1(input: String) = solve(input, 3)

  fun part2(input: String) = solve(input, 26)

  private val NUMERIC_LAYOUT = listOf(
    "789",
    "456",
    "123",
    "#0A",
  )

  private val DIRECTIONAL_LAYOUT = listOf(
    "#^A",
    "<v>",
  )

  private enum class Keypad(val layout: Array<CharArray>, val start: XY, val locations: Map<Char, XY>) {
    NUMERIC(
      layout = NUMERIC_LAYOUT.map { it.toCharArray() }.toTypedArray(),
      start = XY(2, 3),
      locations = NUMERIC_LAYOUT.toLocations()
    ),
    DIRECTIONAL(
      layout = DIRECTIONAL_LAYOUT.map { it.toCharArray() }.toTypedArray(),
      start = XY(2, 0),
      locations = DIRECTIONAL_LAYOUT.toLocations()
    )
  }

  private fun solve(input: String, layers: Int): Long {
    return input.splitNewlines()
      .map {
        val shortestSequence = shortestSequence(SequenceState(Keypad.NUMERIC, it, layers))
        val codeNumber = it.dropLast(1).toInt()
        return@map shortestSequence * codeNumber
      }
      .sum()
  }

  private data class SequenceState(val keypad: Keypad, val target: String, val layers: Int)

  private val SHORTEST_SEQUENCE_MEMO = mutableMapOf<SequenceState, Long>()

  private fun shortestSequence(state: SequenceState): Long {
    return SHORTEST_SEQUENCE_MEMO.getOrPut(state) {
      val (keypad, target, layers) = state
      return@getOrPut allPaths(keypad, target).sumOf { allPaths ->
        if (layers == 1) {
          return@sumOf allPaths.minOf { it.length.toLong() }
        } else {
          return@sumOf allPaths.minOf { path -> shortestSequence(SequenceState(Keypad.DIRECTIONAL, path, layers - 1)) }
        }
      }
    }
  }

  private fun allPaths(keypad: Keypad, target: String): List<List<String>> {
    var curr = keypad.start
    return target.map { char ->
      val end = keypad.locations[char]!!
      val possibilities = possiblePaths(keypad, curr, end)
      curr = end
      return@map possibilities
    }
  }

  private data class PossiblePathsState(val keypad: Keypad, val start: XY, val end: XY)

  private val POSSIBLE_PATHS_MEMO = mutableMapOf<PossiblePathsState, List<String>>()

  private fun possiblePaths(keypad: Keypad, start: XY, end: XY): List<String> {
    // Early exit!
    if (start == end) return listOf("A")

    data class PathState(val xy: XY, val commands: String)

    return POSSIBLE_PATHS_MEMO.getOrPut(PossiblePathsState(keypad, start, end)) {
      val moves = mutableListOf<Direction>()
      val diffX = end.x - start.x
      val diffY = end.y - start.y
      val numSteps = abs(diffX) + abs(diffY)

      if (diffX < 0) {
        moves.add(Direction.W)
      } else if (diffX > 0) {
        moves.add(Direction.E)
      }

      if (diffY < 0) {
        moves.add(Direction.N)
      }
      else if (diffY > 0) {
        moves.add(Direction.S)
      }

      val possibilities = mutableListOf<String>()
      val queue = ArrayDeque<PathState>()
      queue.add(PathState(start, ""))
      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()

        val value = keypad.layout.getXY(curr.xy)
        if (value == null || value == '#') {
          continue
        }

        if (curr.commands.length == numSteps) {
          if (curr.xy == end) {
            possibilities.add(curr.commands + "A")
          }
          continue
        }

        queue.addAll(
          moves.map {
            PathState(curr.xy.move(it), curr.commands + it.toChar())
          }
        )
      }

      return@getOrPut possibilities
    }
  }

  private fun Direction.toChar(): Char {
    return when (this) {
      Direction.N -> '^'
      Direction.E -> '>'
      Direction.S -> 'v'
      Direction.W -> '<'
      else -> throw IllegalArgumentException("Cannot convert $this")
    }
  }

  private fun List<String>.toLocations(): Map<Char, XY> {
    return flatMapIndexed { y, row ->
      row.mapIndexed { x, c ->
        c to XY(x, y)
      }
    }.associate { it }
  }
}
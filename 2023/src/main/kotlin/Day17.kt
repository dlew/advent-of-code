import java.util.PriorityQueue

object Day17 {

  fun part1(input: String) = solve(input, 0, 3)

  fun part2(input: String) = solve(input, 4, 10)

  private enum class Direction { NORTH, EAST, SOUTH, WEST }

  private data class Pos(val x: Int, val y: Int)

  private data class Crucible(val direction: Direction, val pos: Pos, val straightCount: Int)

  private data class State(val crucible: Crucible, val heat: Int)

  private fun solve(input: String, minStraightCount: Int, maxStraightCount: Int): Int {
    val city = input.splitNewlines().map { it.toCharArray().toIntList().toTypedArray() }.toTypedArray()

    val minimumHeat = mutableMapOf<Crucible, Int>()

    // We prioritize reaching the end so we can cut off branching ASAP
    val queue = PriorityQueue<State> { s1, s2 -> s1.heat - s2.heat }

    queue.addAll(
      listOf(
        State(Crucible(Direction.EAST, Pos(0, 0), 0), 0),
        State(Crucible(Direction.SOUTH, Pos(0, 0), 0), 0)
      )
    )

    fun addMoveToQueueIfInBounds(curr: State, direction: Direction) {
      val newPos = curr.crucible.pos.move(direction)
      if (newPos.x in city[0].indices && newPos.y in city.indices) {
        val newStraightCount = if (curr.crucible.direction == direction) curr.crucible.straightCount + 1 else 0
        queue.add(
          State(
            curr.crucible.copy(direction = direction, pos = newPos, straightCount = newStraightCount),
            curr.heat + city[newPos.y][newPos.x]
          )
        )
      }
    }

    val endPos = Pos(city[0].size - 1, city.size - 1)

    while (queue.isNotEmpty()) {
      val curr = queue.poll()

      // Check if we've found the answer
      if (curr.crucible.pos == endPos && curr.crucible.straightCount >= minStraightCount - 1) {
        return curr.heat
      }

      // Have we been here before, but better?
      val best = minimumHeat[curr.crucible]
      if (best != null && best <= curr.heat) {
        continue
      }

      // We record every possible straight count that could've gotten here, since the lowest one is strictly better
      // (EXCEPT we skip it if we need a minimum amount of moves to do anything interesting)
      minimumHeat[curr.crucible] = curr.heat
      if (curr.crucible.straightCount >= minStraightCount) {
        (curr.crucible.straightCount + 1..maxStraightCount).forEach {
          minimumHeat[curr.crucible.copy(straightCount = it)] = curr.heat
        }
      }

      // Try turning left and right
      if (curr.crucible.straightCount >= minStraightCount - 1) {
        if (curr.crucible.direction == Direction.NORTH || curr.crucible.direction == Direction.SOUTH) {
          addMoveToQueueIfInBounds(curr, Direction.EAST)
          addMoveToQueueIfInBounds(curr, Direction.WEST)
        }
        else {
          addMoveToQueueIfInBounds(curr, Direction.NORTH)
          addMoveToQueueIfInBounds(curr, Direction.SOUTH)
        }
      }

      // Try going straight
      if (curr.crucible.straightCount < maxStraightCount - 1) {
        addMoveToQueueIfInBounds(curr, curr.crucible.direction)
      }
    }

    throw IllegalStateException("Didn't find path from start to end!")
  }

  private fun Pos.move(direction: Direction): Pos {
    return when (direction) {
      Direction.NORTH -> copy(y = y - 1)
      Direction.EAST -> copy(x = x + 1)
      Direction.SOUTH -> copy(y = y + 1)
      Direction.WEST -> copy(x = x - 1)
    }
  }

}
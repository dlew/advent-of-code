import models.Direction
import models.XY
import models.getXY
import java.util.*

object Day16 {
  fun part1(input: String) = findAllBestPaths(input).finalNode.score

  fun part2(input: String): Int {
    val (nodes, finalNode) = findAllBestPaths(input)

    // Trace parents to find all possible paths to the start
    val posQueue = ArrayDeque(finalNode.parents)
    val visited = mutableSetOf<Position>()
    while (posQueue.isNotEmpty()) {
      val next = posQueue.remove()
      visited.add(next)
      posQueue.addAll(nodes[next]!!.parents)
    }

    return visited.map { it.xy }.toSet().size + 1 // +1 to count the final node
  }

  private data class BestPaths(val nodes: Map<Position, Node>, val finalNode: Node)

  private data class Node(val score: Int, val parents: MutableSet<Position> = mutableSetOf())

  private data class Reindeer(val pos: Position, val score: Int, val last: Position? = null)

  private data class Position(val xy: XY, val direction: Direction)

  private fun findAllBestPaths(input: String): BestPaths {
    val map = input.splitNewlines().map { it.toCharArray() }.toTypedArray()

    // Start and end are always in the same corners
    val start = XY(x = 1, y = map.size - 2)
    val end = XY(x = map[0].size - 2, y = 1)

    // Priority queue by score (so when we first enter a new Position, it's always the lowest score possible for it)
    val priorityQueue = PriorityQueue<Reindeer> { a, b -> a.score.compareTo(b.score) }
    priorityQueue.add(Reindeer(Position(start, Direction.E), 0, null))

    // Do a breadth-first search (where each Position is a node, not just the XY!), keeping track of parent nodes
    val nodes = mutableMapOf<Position, Node>()
    while (priorityQueue.isNotEmpty()) {
      val reindeer = priorityQueue.remove()

      // See if we've previously found to our current position
      val node = nodes[reindeer.pos]
      if (node != null) {
        // This is another route into this node, add the parent
        if (reindeer.score == node.score) {
          node.parents.add(reindeer.last!!)
        }
        continue
      } else {
        // This is the first time we've seen this location; secure the score & start counting parents
        val parents = mutableSetOf<Position>()
        if (reindeer.last != null) parents.add(reindeer.last)
        nodes[reindeer.pos] = Node(reindeer.score, parents)
      }

      // Try walking forward
      val forward = reindeer.pos.xy.move(reindeer.pos.direction)
      if (map.getXY(forward) != '#') {
        priorityQueue.add(
          reindeer.copy(
            pos = reindeer.pos.copy(xy = forward),
            score = reindeer.score + 1,
            last = reindeer.pos
          )
        )
      }

      // Try turning clockwise & counter-clockwise
      turns(reindeer.pos.direction).forEach { direction ->
        priorityQueue.add(
          reindeer.copy(
            pos = reindeer.pos.copy(direction = direction),
            score = reindeer.score + 1000,
            last = reindeer.pos
          )
        )
      }
    }

    // The "final" node is the one that is at `end` + has the lowest score
    val finalNode = nodes.filter { it.key.xy == end }.minBy { it.value.score }

    return BestPaths(nodes, finalNode.value)
  }

  private fun turns(curr: Direction): List<Direction> {
    if (curr == Direction.N || curr == Direction.S) {
      return listOf(Direction.E, Direction.W)
    }
    return listOf(Direction.N, Direction.S)
  }
}
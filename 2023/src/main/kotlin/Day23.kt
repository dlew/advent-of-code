object Day23 {

  fun part1(input: String): Int {
    val maze = parse(input)
    val start = Pos(maze.first().indexOf('.'), 0)
    val end = Pos(maze.last().indexOf('.'), maze.size - 1)

    val queue = java.util.ArrayDeque<Hike>()
    queue.add(Hike(start.move(Direction.SOUTH), setOf(start)))

    var longest = 0
    while (queue.isNotEmpty()) {
      val (curr, visited) = queue.poll()

      if (curr == end) {
        longest = maxOf(longest, visited.size)
        continue
      }

      val newVisited = visited + curr

      queue.addAll(
        Direction.entries
          .map { it to curr.move(it) }
          .filter { (direction, newPos) ->
            val c = maze[newPos.y][newPos.x]
            return@filter newPos !in visited && c != '#' && (
                c == '.'
                    || (c == '>' && direction == Direction.EAST)
                    || (c == 'v' && direction == Direction.SOUTH)
                    || (c == '<' && direction == Direction.WEST)
                    || (c == '^' && direction == Direction.NORTH)
                )
          }
          .map { (_, newPos) -> Hike(newPos, newVisited) }
      )
    }

    return longest
  }

  fun part2(input: String): Int {
    val maze = parse(input)
    val graph = convertMazeToGraph(maze)
    return longestHike(graph)
  }

  private fun longestHike(graph: Graph): Int {
    val (edges, start, end) = graph

    val queue = java.util.ArrayDeque<Hike>()
    queue.add(Hike(start, setOf(start), 0))

    var longest = 0
    while (queue.isNotEmpty()) {
      val path = queue.poll()

      if (path.curr == end) {
        longest = maxOf(longest, path.distance)
        continue
      }

      // Heuristic that makes it possible to finish this within our lifetime and heap space
      if (!canGetToEnd(graph, path)) {
        continue
      }

      val nextNodes = edges[path.curr]!!.filter { it.key !in path.visited }
      queue.addAll(
        nextNodes.map { Hike(it.key, path.visited + it.key, path.distance + it.value) }
      )
    }

    return longest
  }

  private fun canGetToEnd(graph: Graph, path: Hike): Boolean {
    val visited = HashSet<Pos>(path.visited - path.curr)
    val queue = java.util.ArrayDeque<Pos>()
    queue.add(path.curr)

    while (queue.isNotEmpty()) {
      val curr = queue.poll()

      if (curr in visited) {
        continue
      }
      else if (curr == graph.end) {
        return true
      }

      visited.add(curr)
      queue.addAll(graph.edges[curr]!!.keys)
    }

    return false
  }

  private fun convertMazeToGraph(maze: List<String>): Graph {
    // So we don't have to worry about bounds checks
    val walledMaze = listOf("#".repeat(maze[0].length)) + maze + listOf("#".repeat(maze[0].length))

    val start = Pos(maze.first().indexOf('.'), 1)
    val end = Pos(maze.last().indexOf('.'), maze.size)

    val nodes = mutableSetOf<Pos>()
    val edges = mutableMapOf<Pos, MutableMap<Pos, Int>>()

    fun addEdge(finder: Edge) {
      nodes.addAll(setOf(finder.start, finder.curr))
      edges.getOrPut(finder.start) { mutableMapOf() }[finder.curr] = finder.distance
      edges.getOrPut(finder.curr) { mutableMapOf() }[finder.start] = finder.distance
    }

    val visited = mutableSetOf<Pos>()
    val queue = java.util.ArrayDeque<Edge>()
    queue.add(Edge(start, start, 0))
    while (queue.isNotEmpty()) {
      val finder = queue.poll()
      val (_, curr, distance) = finder

      if (curr in visited) {
        if (curr in nodes) {
          // Don't restart a search from this node, but record edges
          addEdge(finder)
        }
        continue
      }

      visited.add(curr)

      val next = Direction.entries
        .map { curr.move(it) }
        .filter { newPos -> newPos !in visited && walledMaze[newPos.y][newPos.x] != '#' }

      if (next.size == 1) {
        // Keep following the path
        queue.add(finder.copy(curr = next[0], distance = distance + 1))
      } else {
        // We've either hit a fork or a dead-end, record the nodes
        addEdge(finder)
        queue.addAll(next.map { Edge(start = curr, curr = it, distance = 1) })
      }
    }

    return Graph(edges, start, end)
  }

  private data class Graph(val edges: Map<Pos, Map<Pos, Int>>, val start: Pos, val end: Pos)

  private data class Edge(val start: Pos, val curr: Pos, val distance: Int)

  private data class Hike(val curr: Pos, val visited: Set<Pos>, val distance: Int = 0)

  private data class Pos(val x: Int, val y: Int) {
    fun move(direction: Direction) = when (direction) {
      Direction.NORTH -> copy(y = y - 1)
      Direction.EAST -> copy(x = x + 1)
      Direction.SOUTH -> copy(y = y + 1)
      Direction.WEST -> copy(x = x - 1)
    }
  }

  private enum class Direction { NORTH, EAST, SOUTH, WEST }

  private fun parse(input: String) = input.splitNewlines()
}
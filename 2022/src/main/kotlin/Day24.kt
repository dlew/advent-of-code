object Day24 {

  fun part1(input: String): Int {
    val board = parseInput(input)
    return 1 + shortestPath(board, Point(0, -1), Point(board.width - 1, board.height - 1), 0)
  }

  fun part2(input: String): Int {
    val board = parseInput(input)

    val start1 = Point(0, -1)
    val end1 = Point(board.width - 1, board.height - 1)
    val start2 = Point(board.width - 1, board.height)
    val end2 = Point(0, 0)

    val there = 1 + shortestPath(board, start1, end1, 0)
    val back = 1 + shortestPath(board, start2, end2, there)
    return 1 + shortestPath(board, start1, end1, back)
  }

  private fun shortestPath(board: Board, start: Point, end: Point, startMinute: Int): Int {
    val (_, width, height) = board
    val configurations = getAllConfigurations(board)
    val cycleSize = configurations.size

    val initialState = State(startMinute, start)
    val queue = mutableListOf(initialState)
    val explored = mutableSetOf(initialState.toExploredState(cycleSize))
    while (queue.isNotEmpty()) {
      val (minute, position) = queue.removeFirst()

      // We reached the end!
      if (position == end) {
        return minute
      }

      val nextConfig = configurations[(minute + 1) % cycleSize]
      val nextStates = listOf(
        position.copy(x = position.x + 1),
        position.copy(y = position.y + 1),
        position.copy(x = position.x - 1),
        position.copy(y = position.y - 1),
        position
      ).filter {
        (it.x in 0 until width
            && it.y in 0 until height
            && it !in nextConfig)
            || it == start
      }
        .map { State(minute + 1, it) }
        .filter { it.toExploredState(cycleSize) !in explored }

      explored.addAll(nextStates.map { it.toExploredState(cycleSize) })
      queue.addAll(nextStates)
    }

    throw IllegalStateException("Could not find path")
  }

  private fun getAllConfigurations(board: Board): List<Set<Point>> {
    var curr = board
    val boards = mutableListOf(blizzardPoints(curr))
    while(true) {
      curr = cycleBlizzard(curr)
      val currPoints = blizzardPoints(curr)
      if (currPoints !in boards) {
        boards.add(currPoints)
      }
      else {
        return boards
      }
    }
  }

  private fun blizzardPoints(board: Board) = board.blizzards.map { it.point }.toSet()

  private fun cycleBlizzard(board: Board): Board {
    return board.copy(
      blizzards = board.blizzards.map {
        it.copy(
          point = when (it.direction) {
            Direction.NORTH -> it.point.copy(y = (it.point.y - 1 + board.height) % board.height)
            Direction.EAST -> it.point.copy(x = (it.point.x + 1) % board.width)
            Direction.SOUTH -> it.point.copy(y = (it.point.y + 1) % board.height)
            Direction.WEST -> it.point.copy(x = (it.point.x - 1 + board.width) % board.width)
          }
        )
      }.toSet()
    )
  }

  private fun parseInput(input: String): Board {
    val blizzards = mutableSetOf<Blizzard>()
    val lines = input.splitNewlines()

    lines
      .drop(1)
      .dropLast(1)
      .forEachIndexed { y: Int, line: String ->
        line.drop(1).dropLast(1)
          .mapIndexed { x, c ->
            val direction = when (c) {
              '^' -> Direction.NORTH
              '>' -> Direction.EAST
              'v' -> Direction.SOUTH
              '<' -> Direction.WEST
              else -> null
            }

            if (direction != null) {
              blizzards.add(Blizzard(Point(x, y), direction))
            }
          }
      }

    return Board(
      blizzards = blizzards,
      width = lines[0].length - 2,
      height = lines.size - 2
    )
  }

  private enum class Direction { NORTH, EAST, SOUTH, WEST }

  private data class Board(val blizzards: Set<Blizzard>, val width: Int, val height: Int)

  private data class Blizzard(val point: Point, val direction: Direction)

  private data class State(val minute: Int, val position: Point) {
    fun toExploredState(cycleSize: Int) = ExploredState(minute % cycleSize, position)
  }

  // Same as State, only module the cycle size so you can tell when you've repeated yourself
  private data class ExploredState(val cycle: Int, val position: Point)

  private data class Point(val x: Int, val y: Int)

}
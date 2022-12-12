object Day12 {

  fun part1(input: String): Int {
    val map = input.splitNewlines()
    val start = findChar(map, 'S')[0]
    val end = findChar(map, 'E')[0]
    return shortestPath(map, start, end)!!.size - 1
  }

  fun part2(input: String): Int {
    val map = input.splitNewlines()
    val starts = findChar(map, 'a')
    val end = findChar(map, 'E')[0]
    return starts.minOfOrNull { start -> shortestPath(map, start, end)?.size ?: Int.MAX_VALUE }!! - 1
  }

  private fun shortestPath(map: List<String>, start: Pos, end: Pos): List<Pos>? {
    val queue = mutableListOf(listOf(start))
    val explored = mutableSetOf(start)

    while (queue.isNotEmpty()) {
      val path = queue.removeFirst()
      val pos = path.last()

      if (pos == end) {
        return path
      }

      findValidMoves(map, explored, pos)
        .forEach {
          queue.add(path + it)
          explored.add(it)
        }
    }

    return null
  }

  private fun findValidMoves(map: List<String>, explored: Set<Pos>, pos: Pos): List<Pos> {
    return pos
      .adjacent
      .filter { next ->
        inBounds(map, next)
            && next !in explored
            && canMove(map[pos.y][pos.x], map[next.y][next.x])
      }
  }

  private fun inBounds(map: List<String>, pos: Pos) = pos.x in map[0].indices && pos.y in map.indices

  private fun canMove(from: Char, to: Char) = to.height - from.height <= 1

  private val Char.height: Int
    get() = when {
      this == 'S' -> 'a'.code
      this == 'E' -> 'z'.code
      else -> this.code
    }

  private fun findChar(map: List<String>, char: Char): List<Pos> {
    val found = mutableListOf<Pos>()
    map.indices.forEach { y ->
      map[y].indices.forEach { x ->
        if (map[y][x] == char) {
          found.add(Pos(x, y))
        }
      }
    }
    return found
  }

  private data class Pos(val x: Int, val y: Int) {
    val adjacent: List<Pos>
      get() = listOf(
        Pos(x + 1, y),
        Pos(x - 1, y),
        Pos(x, y + 1),
        Pos(x, y - 1)
      )
  }

}
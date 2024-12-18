import models.CARDINAL_DIRECTIONS
import models.XY
import models.getXY

private typealias Memory = Array<CharArray>

// Brute force would've worked fine, but I decided to optimize
object Day18 {
  fun part1(input: String, size: Int, numBytes: Int): Int {
    val memory = createMemory(size)
    val bytes = parseBytes(input)
    loadBytes(memory, bytes.take(numBytes))
    return path(memory)!!.size - 1
  }

  fun part2(input: String, size: Int, safeNumBytes: Int = 0): String {
    val memory = createMemory(size)
    val bytes = parseBytes(input)
    loadBytes(memory, bytes.take(safeNumBytes))

    var path = path(memory)!!
    bytes.drop(safeNumBytes).forEach { byte ->
      memory[byte.y][byte.x] = '#'

      // Rework path every time we get blocked
      if (byte in path) {
        path = path(memory) ?: return "${byte.x},${byte.y}"
      }
    }

    throw IllegalStateException("Unsolvable! Bad input?")
  }

  private fun path(memory: Array<CharArray>): Set<XY>? {
    val queue = ArrayDeque(listOf(Node(XY(0, 0))))
    val visited = mutableSetOf<XY>()
    val exit = XY(memory.size - 1, memory.size - 1)
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val xy = curr.xy

      if (xy == exit) return chartPath(curr)

      if (xy in visited) continue
      visited.add(xy)

      queue.addAll(
        CARDINAL_DIRECTIONS
          .map { Node(xy.move(it), curr) }
          .filter { memory.getXY(it.xy) == '.' }
      )
    }

    return null
  }

  private data class Node(val xy: XY, val prev: Node? = null)

  private fun chartPath(end: Node): Set<XY> {
    val path = mutableSetOf<XY>()
    var node: Node? = end
    while (node != null) {
      path.add(node.xy)
      node = node.prev
    }
    return path
  }

  private fun parseBytes(input: String): List<XY> {
    return input.splitNewlines().map { line ->
      val (x, y) = line.splitCommas().toIntList()
      XY(x, y)
    }
  }

  private fun loadBytes(memory: Memory, bytes: List<XY>) {
    bytes.forEach { (x, y) ->
      memory[y][x] = '#'
    }
  }

  private fun createMemory(size: Int) = Array(size) { CharArray(size) { '.' } }
}
object Day08 {

  private data class Data(val directions: String, val nodes: Map<String, Node>)

  private data class Node(val label: String, val left: String, val right: String)

  fun part1(input: String): Int {
    return cycleLength(parseInput(input), "AAA", "ZZZ")
  }

  fun part2(input: String): Long {
    val maps = parseInput(input)
    val cycles = maps.nodes.keys
      .filter { it.endsWith("A") }
      .map { cycleLength(maps, it, "Z").toLong() }
    return leastCommonMultiple(cycles)
  }

  private fun cycleLength(data: Data, start: String, endsWith: String): Int {
    var count = 0
    var curr = start
    while (!curr.endsWith(endsWith)) {
      curr = when (data.directions[count % data.directions.length]) {
        'L' -> data.nodes[curr]!!.left
        else -> data.nodes[curr]!!.right
      }
      count++
    }
    return count
  }

  private fun leastCommonMultiple(numbers: List<Long>) = numbers.reduce { a, b -> leastCommonMultiple(a, b) }

  private fun leastCommonMultiple(a: Long, b: Long) = a * (b / greatestCommonDivisor(a, b))

  // Euclid's algorithm
  private fun greatestCommonDivisor(a: Long, b: Long): Long = if (b == 0L) a else greatestCommonDivisor(b, a % b)

  private fun parseInput(input: String): Data {
    val lines = input.splitNewlines()
    return Data(
      directions = lines[0],
      nodes = lines
        .drop(2)
        .map { Node(it.slice(0..2), it.slice(7..9), it.slice(12..14)) }
        .associateBy { it.label }
    )
  }

}
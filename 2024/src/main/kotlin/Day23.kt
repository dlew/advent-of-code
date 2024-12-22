private typealias ComputerSet = Set<String>

object Day23 {
  fun part1(input: String): Int {
    val network = parse(input)
    return network
      .keys
      .filter { it.startsWith('t') }
      .flatMap { connectThree(network, it) }
      .toSet()
      .size
  }

  private fun connectThree(network: Map<String, ComputerSet>, first: String): List<ComputerSet> {
    val connectedToFirst = network[first]!!
    return connectedToFirst.flatMap { second ->
      network[second]!!.intersect(connectedToFirst)
        .map { third -> setOf(first, second, third) }
    }
  }

  fun part2(input: String): String {
    val network = parse(input)
    val maxSize = network.maxOf { it.value.size } + 1 // I could cheat and just say 13, but let's not!
    (maxSize downTo 1).forEach { size ->
      network.keys.forEach { key ->
        val success = findCliqueOfSize(network, key, size)
        if (success != null) {
          return success.sorted().joinToString(",")
        }
      }
    }
    throw IllegalArgumentException("Invalid input!")
  }

  private fun findCliqueOfSize(network: Map<String, ComputerSet>, start: String, size: Int): List<String>? {
    val connected = network[start]!!
    val clique = mutableListOf(start)
    connected.forEachIndexed { index, other ->
      // Early exit if it's no longer possible to find a clique of the desired size
      if (size - clique.size > connected.size - index) return null

      if (connected.intersect(network[other]!!).size == size - 2) {
        clique.add(other)
      }
    }

    return if (clique.size == size) clique else null
  }

  private fun parse(input: String): Map<String, ComputerSet> {
    val network = mutableMapOf<String, MutableSet<String>>()
    input.splitNewlines().forEach { line ->
      val a = line.substring(0, 2)
      val b = line.substring(3, 5)
      network.getOrPut(a) { mutableSetOf() }.add(b)
      network.getOrPut(b) { mutableSetOf() }.add(a)
    }
    return network
  }
}
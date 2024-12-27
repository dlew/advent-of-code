object Day25 {
  fun solve(input: String): Int {
    val locks = mutableListOf<IntArray>()
    val keys = mutableListOf<IntArray>()
    input.split("\n\n").forEach {
      val block = it.splitNewlines().map { it.toCharArray() }.toTypedArray()
      val heights = block[0].indices.map { x ->
        block.indices.count { y -> block[y][x] == '#' } - 1
      }.toIntArray()
      if (it.startsWith("#####")) locks.add(heights) else keys.add(heights)
    }

    return locks.sumOf { lock ->
      keys.count { key ->
        lock.indices.all { lock[it] + key[it] < 6 }
      }
    }
  }
}
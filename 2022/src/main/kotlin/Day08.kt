object Day08 {

  fun part1(input: String): Int {
    val trees = input.splitNewlines().map { it.toIntList() }
    val size = trees.size // Assume square input

    return trees.indices.sumOf { y ->
      trees[y].indices.count { x ->
        val tree = trees[y][x]
        return@count (0 until x).all { tree > trees[y][it] }
            || (x + 1 until size).all { tree > trees[y][it] }
            || (0 until y).all { tree > trees[it][x] }
            || (y + 1 until size).all { tree > trees[it][x] }
      }
    }
  }

  fun part2(input: String): Int {
    val trees = input.splitNewlines().map { it.toIntList() }
    val size = trees.size // Assume square input

    return trees.indices.maxOf { y ->
      trees[y].indices.maxOf { x ->
        val tree = trees[y][x]
        return@maxOf (x - 1 downTo 0).asSequence().map { trees[y][it] }.viewingDistance(tree) *
            (x + 1 until size).asSequence().map { trees[y][it] }.viewingDistance(tree) *
            (y - 1 downTo 0).asSequence().map { trees[it][x] }.viewingDistance(tree) *
            (y + 1 until size).asSequence().map { trees[it][x] }.viewingDistance(tree)
      }
    }
  }

  private fun Sequence<Int>.viewingDistance(tree: Int): Int {
    var counter = 0
    for (other in this) {
      if (tree <= other) {
        return counter + 1
      }
      counter++
    }
    return counter
  }

}
object Day11 {

  private data class Pos(val x: Int, val y: Int)

  fun sumPairwisePaths(input: String, expansionSize: Long): Long {
    val image = input.splitNewlines()
    val emptyRows = image.indices.filter { y -> image[y].all { it == '.' } }
    val emptyCols = image[0].indices.filter { x -> image.all { it[x] == '.' } }

    val galaxies = image.flatMapIndexed { y, row ->
      row.indices.filter { x -> row[x] == '#' }.map { x -> Pos(x, y) }
    }

    // Maaanhaaattaaan distance
    return galaxies.withIndex().sumOf { (index, start) ->
      galaxies.drop(index + 1).sumOf { end ->
        val xRange = absIntRange(start.x, end.x)
        val yRange = absIntRange(start.y, end.y)
        val manhattanDistance = xRange.last - xRange.first + yRange.last - yRange.first
        val numExpansions = emptyCols.count { it in xRange } + emptyRows.count { it in yRange }
        (manhattanDistance - numExpansions) + (expansionSize * numExpansions)
      }
    }
  }

  private fun absIntRange(a: Int, b: Int) = if (a < b) a..b else b..a

}
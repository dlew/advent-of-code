object Day14 {

  fun part1(input: String): Int {
    val grid = input.splitNewlines()

    // Calculates total load *as if* it were tilted north
    return grid[0].indices.sumOf { x ->
      var totalLoad = 0
      var nextLoad = grid.size
      grid.indices.forEach { y ->
        when (grid[y][x]) {
          '#' -> nextLoad = grid.size - y - 1
          'O' -> totalLoad += nextLoad--
        }
      }
      return@sumOf totalLoad
    }
  }

  fun part2(input: String): Int {
    var grid = input.splitNewlines()
    val memo = mutableMapOf<List<String>, Int>()
    val repetitions = 1000000000
    repeat(repetitions) { iteration ->
      val startOfCycle = memo[grid]
      if (startOfCycle != null) {
        val cycleSize = memo.size - startOfCycle
        val cycleSpot = (repetitions - startOfCycle) % cycleSize
        val position = startOfCycle + cycleSpot
        return calculateTotalLoad(memo.entries.find { it.value == position }!!.key)
      }

      memo[grid] = iteration
      grid = spinCycle(grid)
    }

    throw IllegalStateException("Did not find a cycle!")
  }

  // Repetitive, but it runs faster than tilt north + rotate 4 times
  private fun spinCycle(grid: List<String>): List<String> {
    val mutableGrid = grid.map { it.toCharArray() }.toTypedArray()

    // Tilt North
    mutableGrid[0].indices.forEach { x ->
      var nextY = 0
      mutableGrid.indices.forEach { y ->
        when (mutableGrid[y][x]) {
          '#' -> nextY = y + 1
          'O' -> {
            mutableGrid[y][x] = '.'
            mutableGrid[nextY++][x] = 'O'
          }
        }
      }
    }

    // Tilt West
    mutableGrid.indices.forEach { y ->
      var nextX = 0
      mutableGrid[0].indices.forEach { x ->
        when (mutableGrid[y][x]) {
          '#' -> nextX = x + 1
          'O' -> {
            mutableGrid[y][x] = '.'
            mutableGrid[y][nextX++] = 'O'
          }
        }
      }
    }

    // Tilt South
    mutableGrid[0].indices.forEach { x ->
      var nextY = mutableGrid.size - 1
      mutableGrid.indices.reversed().forEach { y ->
        when (mutableGrid[y][x]) {
          '#' -> nextY = y - 1
          'O' -> {
            mutableGrid[y][x] = '.'
            mutableGrid[nextY--][x] = 'O'
          }
        }
      }
    }

    // Tilt East
    mutableGrid.indices.forEach { y ->
      var nextX = mutableGrid[0].size - 1
      mutableGrid[0].indices.reversed().forEach { x ->
        when (mutableGrid[y][x]) {
          '#' -> nextX = x - 1
          'O' -> {
            mutableGrid[y][x] = '.'
            mutableGrid[y][nextX--] = 'O'
          }
        }
      }
    }

    return mutableGrid.map { String(it) }
  }

  private fun calculateTotalLoad(grid: List<String>): Int {
    return grid[0].indices
      .sumOf { x ->
        grid.indices
          .filter { y -> grid[y][x] == 'O' }
          .sumOf { y -> grid.size - y }
      }
  }

}
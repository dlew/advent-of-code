object Day03 {

  data class Pos(val x: Int, val y: Int)

  data class PartWithSymbol(val number: Int, val symbol: Char, val symbolPos: Pos)

  fun part1(input: String): Int {
    return getPartsWithSymbols(input).sumOf { it.number }
  }

  fun part2(input: String): Int {
    return getPartsWithSymbols(input)
      .filter { it.symbol == '*' }
      .groupBy { it.symbolPos }
      .values
      .filter { it.size == 2 }
      .sumOf { it[0].number * it[1].number }
  }

  private fun getPartsWithSymbols(input: String): List<PartWithSymbol> {
    val grid = input.splitNewlines().map { it.toCharArray() }
    val partsWithSymbols = mutableListOf<PartWithSymbol>()

    fun addPart(number: Int, symbolPos: Pos) {
      partsWithSymbols.add(PartWithSymbol(number, grid[symbolPos.y][symbolPos.x], symbolPos))
    }

    for (row in grid.indices) {
      var currNumber = 0
      var symbolPos: Pos? = null

      for (col in grid[row].indices) {
        val curr = grid[row][col]

        if (curr.isDigit()) {
          currNumber = currNumber * 10 + curr.digitToInt()
          if (symbolPos == null) {
            symbolPos = findSymbol(grid, row, col)
          }
        } else if (currNumber != 0) {
          if (symbolPos != null) {
            addPart(currNumber, symbolPos)
            symbolPos = null
          }
          currNumber = 0
        }
      }

      if (currNumber != 0 && symbolPos != null) {
        addPart(currNumber, symbolPos)
      }
    }

    return partsWithSymbols
  }

  private fun findSymbol(grid: List<CharArray>, row: Int, col: Int): Pos? {
    ((row - 1).coerceAtLeast(0)..(row + 1).coerceAtMost(grid.size - 1)).forEach { y ->
      ((col - 1).coerceAtLeast(0)..(col + 1).coerceAtMost(grid[0].size - 1)).forEach { x ->
        val curr = grid[y][x]
        if (curr != '.' && !curr.isDigit()) {
          return Pos(x, y)
        }
      }
    }

    return null
  }

}
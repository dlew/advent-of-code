object Day05 {

  fun part1(input: String): String {
    val inputs = parseInput(input)
    val crates = inputs.crates.toMutableList()
      inputs.moves.forEach { (number, from, to) ->
      crates[to] = crates[to] + crates[from].takeLast(number).reversed()
      crates[from] = crates[from].dropLast(number)
    }
    return crates.getTopCrates()
  }

  fun part2(input: String): String {
    val inputs = parseInput(input)
    val crates = inputs.crates.toMutableList()
    inputs.moves.forEach { (number, from, to) ->
      crates[to] = crates[to] + crates[from].takeLast(number)
      crates[from] = crates[from].dropLast(number)
    }
    return crates.getTopCrates()
  }

  private fun List<List<Char>>.getTopCrates() = this.map { it.last() }.joinToString("")

  private data class Input(val crates: List<List<Char>>, val moves: List<Move>)

  private data class Move(val number: Int, val from: Int, val to: Int)

  private fun parseInput(input: String): Input {
    val (crateInput, moveInput) = input.split("\n\n")
    return Input(parseCrateInput(crateInput), parseMoves(moveInput))
  }

  private fun parseCrateInput(input: String): List<List<Char>> {
    val crates = mutableListOf<MutableList<Char>>()

    input
      .split("\n")
      .reversed()
      .drop(1)
      .forEach { line ->
        line.chunked(4).forEachIndexed { index, chunk ->
          if (crates.size == index) {
            crates.add(mutableListOf())
          }

          if (chunk[1] != ' ') {
            crates[index].add(chunk[1])
          }
        }
      }

    return crates
  }

  private fun parseMoves(input: String): List<Move> {
    val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    return input
      .splitNewlines()
      .map { regex.matchEntire(it)!!.destructured }
      .map { (number, from, to) -> Move(number.toInt(), from.toInt() - 1, to.toInt() - 1) }
  }

}
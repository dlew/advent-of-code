import Color.BLUE
import Color.GREEN
import Color.RED

private enum class Color {
  RED, GREEN, BLUE
}

private typealias Cubes = Map<Color, Int>

private data class Game(val num: Int, val draws: List<Cubes>)

object Day02 {

  fun part1(input: String): Int {
    return input.splitNewlines()
      .map { parseGame(it) }
      .filter { it.isPossible() }
      .sumOf { it.num }
  }

  fun part2(input: String): Int {
    return input.splitNewlines()
      .map { parseGame(it) }
      .map { it.calculateMinimumCubes() }
      .sumOf { it.power() }
  }

  private fun Game.isPossible() = this.draws.all {
    it.getOrDefault(RED, 0) <= 12
        && it.getOrDefault(GREEN, 0) <= 13
        && it.getOrDefault(BLUE, 0) <= 14
  }

  private fun Game.calculateMinimumCubes(): Cubes {
    val minCubes = mutableMapOf<Color, Int>()
    this.draws.forEach { draw ->
      Color.entries.forEach { color ->
        minCubes[color] = maxOf(minCubes.getOrDefault(color, 0), draw.getOrDefault(color, 0))
      }
    }
    return minCubes
  }

  private fun Cubes.power() = this.values.reduce(Int::times)

  // Parsing

  private val GAME_REGEX = Regex("Game (\\d+): (.*)")
  private val DRAW_REGEX = Regex("(\\d+) (red|green|blue)")

  private fun parseGame(game: String): Game {
    val match = GAME_REGEX.matchEntire(game)!!
    val number = match.groups[1]!!.value.toInt()
    val draws = match.groups[2]!!.value.trim().split("; ")
    val parsedDraws = draws.map { draw ->
      DRAW_REGEX.findAll(draw)
        .associate { Color.valueOf(it.groups[2]!!.value.uppercase()) to it.groups[1]!!.value.toInt() }
    }
    return Game(number, parsedDraws)
  }

}

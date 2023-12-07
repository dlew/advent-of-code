import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

object Day06 {

  private data class Race(val time: Long, val distance: Long)

  fun part1(input: String): Long {
    val (times, distances) = input.splitNewlines().map { it.splitWhitespace().drop(1).toLongList() }
    val races = times.zip(distances) { time, distance -> Race(time, distance) }
    return races
      .map { it.calculateNumWaysToWin() }
      .reduce(Long::times)
  }

  fun part2(input: String): Long {
    val (time, distance) = input.splitNewlines().map { it.splitWhitespace().drop(1).joinToString("").toLong() }
    return Race(time, distance).calculateNumWaysToWin()
  }

  private fun Race.quadraticRoots(): Pair<Double, Double> {
    val a = -1.0
    val b = time.toDouble()
    val c = -distance.toDouble()
    val start = (-b + sqrt(b.pow(2) - (4*a*c))) / (2 * a)
    val end = (-b - sqrt(b.pow(2) - (4*a*c))) / (2 * a)
    return start to end
  }

  private fun Race.calculateNumWaysToWin(): Long {
    val (start, end) = quadraticRoots()
    // Adjust numbers sliiiiightly to account for less than and greater than (in case of int roots)
    return floor(end - .0001).toLong() - ceil(start + .0001).toLong() + 1
  }
}
import kotlin.math.abs

object Day24 {

  fun part1(input: String, testRange: LongRange): Int {
    val hailstones = parse(input)
    return hailstones
      .mapIndexed { a, h1 ->
        hailstones.subList(a + 1, hailstones.size).count { h2 ->
          willCollideXy(h1, h2, testRange)
        }
      }
      .sum()
  }

  // I'm absolute shit at thinking in more than 2 dimensions, so I just implemented a solution someone came up with
  // here: https://www.reddit.com/r/adventofcode/comments/18pnycy/2023_day_24_solutions/keqf8uq/
  fun part2(input: String): Long {
    val hailstones = parse(input)

    // Find the velocity of the hailstone
    fun findVelocity(hailstones: List<Hailstone>, position: (Hailstone) -> Long, velocity: (Hailstone) -> Long): Long {
      val possibilities = (-300L..300L).toMutableList()
      hailstones.forEachIndexed { index, h1 ->
        hailstones.drop(index + 1).forEach { h2 ->
          if (velocity(h1) == velocity(h2)) {
            val distance = abs(position(h1) - position(h2))
            possibilities.removeIf {
              val testVelocity = velocity(h1) - it
              testVelocity == 0L || distance % testVelocity != 0L
            }

            if (possibilities.size == 1) {
              return possibilities[0]
            }
          }
        }
      }
      throw IllegalStateException("Didn't work!")
    }

    val velocity = XYZ(
      x = findVelocity(hailstones, { it.position.x }, { it.velocity.x }),
      y = findVelocity(hailstones, { it.position.y }, { it.velocity.y }),
      z = findVelocity(hailstones, { it.position.z }, { it.velocity.z })
    )

    // Find the intersection between two hailstones minus the velocity
    val h1 = hailstones[0].copy(
      velocity = XYZ(
        x = hailstones[0].velocity.x - velocity.x,
        y = hailstones[0].velocity.y - velocity.y,
        z = hailstones[0].velocity.z - velocity.z
      )
    )
    val h2 = hailstones[1].copy(
      velocity = XYZ(
        x = hailstones[1].velocity.x - velocity.x,
        y = hailstones[1].velocity.y - velocity.y,
        z = hailstones[1].velocity.z - velocity.z
      )
    )

    val (x, y) = intercept(h1, h2, { it.y }, { it.x })!!
    val (z) = intercept(h1, h2, { it.y }, { it.z })!!

    return x + y + z
  }

  private fun intercept(h1: Hailstone, h2: Hailstone, d1: (XYZ) -> Long, d2: (XYZ) -> Long): Pair<Long, Long>? {
    val m1 = d1(h1.velocity).toDouble() / d2(h1.velocity)
    val m2 = d1(h2.velocity).toDouble() / d2(h2.velocity)

    if (m1 == m2) {
      return null
    }

    val b1 = d1(h1.position) - ((d1(h1.velocity) * d2(h1.position)) / d2(h1.velocity))
    val b2 = d1(h2.position) - ((d1(h2.velocity) * d2(h2.position)) / d2(h2.velocity))

    val top = d1(h1.velocity) * d2(h2.velocity) - d1(h2.velocity) * d2(h1.velocity)
    val bottom = d2(h1.velocity) * d2(h2.velocity)
    val a = (bottom * (b2 - b1)) / top
    val b = (d1(h1.velocity) * a) / d2(h1.velocity) + b1

    return a to b
  }

  private fun willCollideXy(h1: Hailstone, h2: Hailstone, testRange: LongRange): Boolean {
    val intercept = interceptXy(h1, h2)
    return intercept != null
        && intercept.x >= testRange.first
        && intercept.x <= testRange.last
        && intercept.y >= testRange.first
        && intercept.y <= testRange.last
        && h1.velocity.x < 0 != intercept.x > h1.position.x
        && h2.velocity.x < 0 != intercept.x > h2.position.x
  }

  private fun interceptXy(hailstone1: Hailstone, hailstone2: Hailstone): Intercept? {
    val m1 = hailstone1.velocity.y.toDouble() / hailstone1.velocity.x
    val m2 = hailstone2.velocity.y.toDouble() / hailstone2.velocity.x

    if (m1 == m2) {
      return null
    }

    val b1 = hailstone1.position.y - (m1 * hailstone1.position.x)
    val b2 = hailstone2.position.y - (m2 * hailstone2.position.x)

    val x = (b2 - b1) / (m1 - m2)
    val y = (m1 * x) + b1

    return Intercept(x, y, 0.0)
  }

  private data class XYZ(val x: Long, val y: Long, val z: Long)

  private data class Intercept(val x: Double, val y: Double, val z: Double)

  private data class Hailstone(val position: XYZ, val velocity: XYZ)

  private fun parse(input: String): List<Hailstone> {
    val regex = Regex("(\\d+),\\s+(\\d+),\\s+(\\d+)\\s+@\\s+(-?\\d+),\\s+(-?\\d+),\\s+(-?\\d+)")
    return input.splitNewlines().map {
      val match = regex.matchEntire(it)!!
      return@map Hailstone(
        position = XYZ(
          x = match.groupValues[1].toLong(),
          y = match.groupValues[2].toLong(),
          z = match.groupValues[3].toLong(),
        ),
        velocity = XYZ(
          x = match.groupValues[4].toLong(),
          y = match.groupValues[5].toLong(),
          z = match.groupValues[6].toLong(),
        )
      )
    }
  }
}
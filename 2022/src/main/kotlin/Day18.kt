import kotlin.math.abs

object Day18 {

  fun part1(input: String) = surfaceArea(parseInput(input))

  fun part2(input: String): Int {
    val droplet = parseInput(input)

    // Check every location within the droplet's bounds to see if it's in an air pocket or not
    //
    // This is not particularly efficient (we could determine from previous searches whether
    // a point is in an air pocket already) but it runs in <1s so I don't care to optimize
    val bounds = Bounds(droplet)
    val airPockets =
      (bounds.xRange).flatMap { x ->
        (bounds.yRange).flatMap { y ->
          (bounds.zRange).map { z ->
            Cube(x, y, z)
          }
        }
      }.filter { it.isInAirPocket(droplet, bounds) }

    // Check surface area (as if the air pocket was filled in)
    return surfaceArea(droplet + airPockets)
  }

  private fun surfaceArea(droplet: Set<Cube>): Int {
    return droplet.sumOf { cube -> 6 - droplet.count { distance(cube, it) == 1 } }
  }

  private fun distance(a: Cube, b: Cube) = abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)

  private fun Cube.isInAirPocket(droplet: Set<Cube>, bounds: Bounds): Boolean {
    if (this in droplet) return false // Quick exit - can't be an air pocket if actually part of droplet!

    val queue = mutableListOf(this)
    val explored = mutableSetOf(this)

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()

      // We found a way to get outside the bounds of the droplet, must not be in an air pocket
      if (curr.x !in bounds.xRange || curr.y !in bounds.yRange || curr.z !in bounds.zRange) {
        return false
      }

      val next = listOf(
        curr.copy(x = curr.x - 1),
        curr.copy(x = curr.x + 1),
        curr.copy(y = curr.y - 1),
        curr.copy(y = curr.y + 1),
        curr.copy(z = curr.z - 1),
        curr.copy(z = curr.z + 1),
      ).filter { it !in explored && it !in droplet }

      explored.addAll(next)
      queue.addAll(next)
    }

    // Ran out of places to go, we must be in an air pocket
    return true
  }

  private fun parseInput(input: String): Set<Cube> {
    return input.splitNewlines()
      .map { it.splitCommas().map(String::toInt) }
      .map { (x, y, z) -> Cube(x, y, z) }
      .toSet()
  }

  private data class Cube(val x: Int, val y: Int, val z: Int)

  private class Bounds(droplet: Set<Cube>) {
    val xRange = droplet.minOf { it.x }..droplet.maxOf { it.x }
    val yRange = droplet.minOf { it.y }..droplet.maxOf { it.y }
    val zRange = droplet.minOf { it.z }..droplet.maxOf { it.z }
  }

}
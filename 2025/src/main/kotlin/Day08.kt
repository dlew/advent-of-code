import models.XYZ
import utils.splitNewlines
import kotlin.math.pow
import kotlin.math.sqrt

object Day08 {
  private data class Distance(val a: XYZ, val b: XYZ, val distance: Double)

  fun part1(input: String, numConnections: Int): Int {
    val junctionBoxes = parse(input)
    val distances = calculateDistances(junctionBoxes)

    val clusters = junctionBoxes.map { setOf(it) }.toMutableSet()
    distances.take(numConnections).forEach { (a, b) -> cluster(clusters, a, b) }

    return clusters
      .map { it.size }
      .sortedDescending()
      .take(3)
      .reduce(Int::times)
  }

  fun part2(input: String): Long {
    val junctionBoxes = parse(input)
    val distances = calculateDistances(junctionBoxes)

    val clusters = junctionBoxes.map { setOf(it) }.toMutableSet()
    distances.forEach { (a, b) ->
      cluster(clusters, a, b)
      if (clusters.size == 1) return a.x.toLong() * b.x.toLong()
    }

    throw IllegalStateException("How did you get here?!")
  }

  private fun calculateDistances(junctionBoxes: List<XYZ>): List<Distance> {
    return junctionBoxes.flatMapIndexed { index, a ->
      junctionBoxes.drop(index + 1).map { b -> Distance(a, b, distance(a, b)) }
    }.sortedBy { it.distance }
  }

  private fun distance(a: XYZ, b: XYZ): Double {
    return sqrt(
      (a.x - b.x).toDouble().pow(2.0) +
          (a.y - b.y).toDouble().pow(2.0) +
          (a.z - b.z).toDouble().pow(2.0)
    )
  }

  private fun cluster(clusters: MutableSet<Set<XYZ>>, a: XYZ, b: XYZ) {
    val clusterA = clusters.find { a in it }!!
    if (b in clusterA) return // These two are already in the same cluster!
    val clusterB = clusters.find { b in it }!!
    clusters.remove(clusterA)
    clusters.remove(clusterB)
    clusters.add(clusterA + clusterB)
  }

  private fun parse(input: String): List<XYZ> {
    return input
      .splitNewlines()
      .map { line ->
        val (x, y, z) = line.split(",")
        XYZ(x.toInt(), y.toInt(), z.toInt())
      }
  }
}
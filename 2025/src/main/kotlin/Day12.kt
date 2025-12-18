import utils.splitNewlines
import utils.splitWhitespace
import utils.toIntList

// This COULD be a really hard problem, but it turns out that a simple check of whether the region can support
// the unarranged, total area of the shapes is close enough!
object Day12 {
  fun part1(input: String): Int {
    val sections = input.split("\n\n")

    val shapeAreas = sections.dropLast(1).map { shape -> shape.count { it == '#' } }

    return sections.last().splitNewlines().count { region ->
      val (size, quantities) = region.split(":")
      val (width, height) = size.split("x")
      val regionArea = width.toInt() * height.toInt()
      val totalShapeArea = shapeAreas
        .zip(quantities.splitWhitespace().toIntList())
        .sumOf { (a, b) -> a * b }
      return@count regionArea >= totalShapeArea
    }
  }
}
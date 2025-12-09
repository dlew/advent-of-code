import models.XY
import utils.splitCommas
import utils.splitNewlines
import kotlin.math.abs

object Day09 {
  private data class Rectangle(val a: XY, val b: XY, val area: Long)

  fun part1(input: String) = computeAreas(parse(input)).maxOf { it.area }

  fun part2(input: String): Long {
    val redTiles = parse(input)
    val rectangles = computeAreas(redTiles).sortedByDescending { it.area }

    // Compute green lines, sorted such that it's always top-left -> bottom-right
    val greenLines = (redTiles + redTiles.first()).zipWithNext().map { (a, b) ->
      if (b.x > a.x || b.y > a.y) a to b else b to a
    }

    // We want to find the first rectangle that is not bisected by any green line
    return rectangles.first { (a, b) ->
      // Define the lines that make up the rectangle (always top-left -> bottom-right)
      val (minX, maxX) = if (a.x > b.x) listOf(b.x, a.x) else listOf(a.x, b.x)
      val (minY, maxY) = if (a.y > b.y) listOf(b.y, a.y) else listOf(a.y, b.y)
      val rectLines = listOf(
        XY(minX, minY) to XY(maxX, minY), // Top
        XY(maxX, minY) to XY(maxX, maxY), // Right
        XY(minX, maxY) to XY(maxX, maxY), // Bottom
        XY(minX, minY) to XY(minX, maxY), // Left
      )

      // For every green line, check that it doesn't bisect any of the rectangle's lines
      greenLines.none { (greenLineA, greenLineB) ->
        val greenLineVertical = greenLineA.x == greenLineB.x

        rectLines.any { (rectLineA, rectLineB) ->
          val areaLineVertical = rectLineA.x == rectLineB.x

          // Lines are parallel to each other - they won't mess each other up
          if (greenLineVertical == areaLineVertical) return@any false

          // Check that the green line doesn't directly intersect OR bisect the rectangle line
          if (greenLineVertical) {
            // The green line's X must be BETWEEN the rectangle line (to bisect), and the Y must touch at some point
            return@any greenLineA.x in rectLineA.x + 1 until rectLineB.x && rectLineA.y in greenLineA.y..greenLineB.y
          } else {
            // The green line's Y must be BETWEEN the rectangle line (to bisect), and the X must touch at some point
            return@any greenLineA.y in rectLineA.y + 1 until rectLineB.y && rectLineA.x in greenLineA.x..greenLineB.x
          }
        }
      }
    }.area
  }

  private fun computeAreas(redTiles: List<XY>): List<Rectangle> {
    return redTiles.dropLast(1).withIndex().flatMap { (index, a) ->
      redTiles.drop(index + 1).map { b ->
        Rectangle(a, b, (abs(a.x - b.x) + 1).toLong() * (abs(a.y - b.y) + 1))
      }
    }
  }

  private fun parse(input: String): List<XY> {
    return input.splitNewlines().map { line ->
      val (x, y) = line.splitCommas()
      XY(x.toInt(), y.toInt())
    }
  }
}
import Day17.Direction.LEFT
import Day17.Direction.RIGHT

object Day17 {

  fun part1(input: String): Int {
    val chamber = Chamber(parseInput(input))
    repeat(2022) { chamber.dropNext() }
    return chamber.height()
  }

  fun part2(input: String, testSize: Int, startingCycleSize: Int): Long {
    val chamber = Chamber(parseInput(input))

    val heightChanges = (0..testSize)
      .map {
        chamber.dropNext()
        chamber.height()
      }
      .windowed(2)
      .map { (prev, next) -> next - prev }

    // Automatically detect the cycle and calculate the value based on that
    var cycleSize = startingCycleSize
    while (cycleSize < heightChanges.size) {
      val cycleStart = cycleExists(heightChanges, cycleSize)

      // If we detected a cycle, we can calculate the total now
      if (cycleStart != null) {
        val initialSum = heightChanges.take(cycleStart).sum()
        val cycleSum = heightChanges.drop(cycleStart).take(cycleSize).sum()
        val iterationsAfterStart = 1000000000000L - cycleStart
        val numCycles = iterationsAfterStart / cycleSize
        val cycleRemainder = (iterationsAfterStart % cycleSize).toInt()
        val remainderSum = heightChanges.drop(cycleStart).take(cycleRemainder).sum()
        return initialSum + (cycleSum * numCycles) + remainderSum
      }
      cycleSize++
    }

    throw IllegalStateException("Could not find a cycle in changes of size ${heightChanges.size}")
  }

  // This is admittedly a rather slow & janky way to detect cycles w/ initial start
  private fun cycleExists(heightChanges: List<Int>, cycleSize: Int): Int? {
    return (0 until heightChanges.size - (cycleSize * 2)).find { start ->
      heightChanges
        .asSequence()
        .drop(start)
        .chunked(cycleSize)
        .take(4)
        .zipWithNext()
        .all { (first, second) ->
          if (first.size == second.size) first == second else true
        }
    }
  }

  private class Chamber(private val jetPattern: List<Direction>) {
    private val rocks = mutableSetOf<Point>()
    private var shapeToSpawn = Shape.HORIZONTAL_LINE
    private var jetIndex = 0

    fun dropNext() {
      var shape = shapeToSpawn.spawn(2, nextRockStartingHeight())
      incrementShape()

      while (true) {
        // Blow with jet left or right
        val direction = jetPattern[jetIndex]
        shape = when (direction) {
          LEFT -> moveLeft(shape)
          RIGHT -> moveRight(shape)
        }
        incrementJetIndex()

        // Move downwards
        val downShape = moveDown(shape)

        // It didn't move downwards, must be stuck, go on
        if (downShape == shape) {
          break
        }

        shape = downShape
      }

      // Add shape to settled rocks
      rocks.addAll(shape)
    }

    fun height() = if (rocks.isEmpty()) 0 else rocks.maxOf { it.y } + 1

    private fun moveLeft(shape: List<Point>): List<Point> {
      val moved = shape.map { it.copy(x = it.x - 1) }
      return if (validLocation(moved)) moved else shape
    }

    private fun moveRight(shape: List<Point>): List<Point> {
      val moved = shape.map { it.copy(x = it.x + 1) }
      return if (validLocation(moved)) moved else shape
    }

    private fun moveDown(shape: List<Point>): List<Point> {
      val moved = shape.map { it.copy(y = it.y - 1) }
      return if (validLocation(moved)) moved else shape
    }

    private fun validLocation(shape: List<Point>): Boolean {
      return shape.all { it.x in 0..6 && it.y >= 0 && it !in rocks }
    }

    private fun nextRockStartingHeight() = height() + 3

    private fun incrementJetIndex() {
      jetIndex = (jetIndex + 1) % jetPattern.size
    }

    private fun incrementShape() {
      shapeToSpawn = shapeToSpawn.next()
    }
  }

  private fun parseInput(input: String) = input.map { if (it == '<') LEFT else RIGHT }

  private data class Point(val x: Int, val y: Int)

  private enum class Direction {
    LEFT,
    RIGHT
  }

  private enum class Shape {
    HORIZONTAL_LINE,
    PLUS,
    BACKWARDS_L,
    VERTICAL_LINE,
    SQUARE;

    fun next(): Shape {
      val values = Shape.values()
      return values[(this.ordinal + 1) % values.size]
    }

    fun spawn(left: Int, bottom: Int): List<Point> {
      return when (this) {
        HORIZONTAL_LINE -> listOf(
          Point(left, bottom),
          Point(left + 1, bottom),
          Point(left + 2, bottom),
          Point(left + 3, bottom),
        )

        PLUS -> listOf(
          Point(left + 1, bottom + 2),
          Point(left, bottom + 1),
          Point(left + 1, bottom + 1),
          Point(left + 2, bottom + 1),
          Point(left + 1, bottom)
        )

        BACKWARDS_L -> listOf(
          Point(left + 2, bottom + 2),
          Point(left + 2, bottom + 1),
          Point(left, bottom),
          Point(left + 1, bottom),
          Point(left + 2, bottom),
        )

        VERTICAL_LINE -> listOf(
          Point(left, bottom + 3),
          Point(left, bottom + 2),
          Point(left, bottom + 1),
          Point(left, bottom),
        )

        SQUARE -> listOf(
          Point(left, bottom + 1),
          Point(left + 1, bottom + 1),
          Point(left, bottom),
          Point(left + 1, bottom),
        )
      }
    }
  }

}
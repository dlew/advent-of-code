object Day22 {

  fun part1(input: String): Int {
    val bricks = parse(input).sortedBy { it.zRange.first }
    val (settled) = dropBricks(bricks)
    return settled.count { brick ->
      val bricksWithDisintegration = settled.filter { it != brick }
      val (_, numBricksFallen) = dropBricks(bricksWithDisintegration)
      return@count numBricksFallen == 0
    }
  }

  fun part2(input: String): Int {
    val bricks = parse(input).sortedBy { it.zRange.first }
    val (settled) = dropBricks(bricks)
    return settled.sumOf { brick ->
      val bricksWithDisintegration = settled.filter { it != brick }
      val (_, numBricksFallen) = dropBricks(bricksWithDisintegration)
      return@sumOf numBricksFallen
    }
  }

  private fun dropBricks(bricks: List<Brick>): DropResult {
    val settled = mutableListOf<Brick>()
    var numBricksFallen = 0
    bricks.forEach { brick ->
      var fallingBrick = brick
      var fell = false
      while (true) {
        // Resting on the ground
        if (fallingBrick.zRange.first == 1) {
          settled.add(fallingBrick)
          break
        }

        // Resting on another brick
        if (settled.any { it.supports(fallingBrick) }) {
          settled.add(fallingBrick)
          break
        }

        fallingBrick = fallingBrick.fall()
        fell = true
      }

      if (fell) {
        numBricksFallen++
      }
    }

    return DropResult(settled, numBricksFallen)
  }

  private data class DropResult(val bricks: List<Brick>, val numBricksFallen: Int)

  private data class Pos(val x: Int, val y: Int, val z: Int)

  private data class Brick(val start: Pos, val end: Pos) {
    val xRange = if (start.x < end.x) start.x..end.x else end.x..start.x
    val yRange = if (start.y < end.y) start.y..end.y else end.y..start.y
    val zRange = if (start.z < end.z) start.z..end.z else end.z..start.z

    fun fall() = copy(
      start = start.copy(z = start.z - 1),
      end = end.copy(z = end.z - 1)
    )

    fun supports(other: Brick): Boolean {
      return zRange.last == other.zRange.first - 1
          && xRange.last >= other.xRange.first
          && xRange.first <= other.xRange.last
          && yRange.last >= other.yRange.first
          && yRange.first <= other.yRange.last
    }
  }

  private fun parse(input: String): List<Brick> {
    val regex = Regex("(\\d+),(\\d+),(\\d+)~(\\d+),(\\d+),(\\d+)")
    return input.splitNewlines().map {
      val match = regex.matchEntire(it)!!
      return@map Brick(
        start = Pos(match.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt()),
        end = Pos(match.groupValues[4].toInt(), match.groupValues[5].toInt(), match.groupValues[6].toInt()),
      )
    }
  }
}
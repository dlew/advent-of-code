import Day22.Direction.*

object Day22 {

  fun part1(input: String): Int {
    val (map, commands) = parseInput(input)
    var position = findStart(map)
    var facing = EAST

    for (command in commands) {
      when (command) {
        is Command.TurnLeft -> facing = turnLeft(facing)
        is Command.TurnRight -> facing = turnRight(facing)
        is Command.Move -> {
          repeat(command.steps) {
            position = simpleStep(map, position, facing)
          }
        }
      }
    }

    return score(position.x + 1, position.y + 1, facing)
  }

  fun part2(input: String): Int {
    val (faces, commands) = parseCube(input)
    var position = CubePosition(0, 0, 0, EAST)

    for (command in commands) {
      when (command) {
        is Command.TurnLeft -> position = position.copy(facing = turnLeft(position.facing))
        is Command.TurnRight -> position = position.copy(facing = turnRight(position.facing))
        is Command.Move -> {
          repeat(command.steps) {
            position = cubeStep(faces, position)
          }
        }
      }
    }

    val finalCube = faces[position.cubeFace]
    val overallX = position.x + finalCube.corner.x
    val overallY = position.y + finalCube.corner.y
    val facing = position.facing

    return score(overallX, overallY, facing)
  }

  private fun cubeStep(faces: List<CubeFace>, position: CubePosition): CubePosition {
    val nextPosition = nextCubePosition(faces, position)
    return if (faces[nextPosition.cubeFace].face[nextPosition.y][nextPosition.x] == '.') nextPosition else position
  }

  // Finds the next position (but does not care whether there is a wall there or not)
  //
  // The wrapping is based upon the input cube shape (or it won't work). Specifically must have this shape:
  //
  //  **
  //  *
  // **
  // *
  //
  // This is not at all elegant. Find a graphics programmer if you want some cool 3D algorithms.
  private fun nextCubePosition(faces: List<CubeFace>, position: CubePosition): CubePosition {
    val maxPos = faces[0].face.size - 1

    when (position.facing) {
      EAST -> {
        if (position.x < maxPos) {
          return position.copy(x = position.x + 1)
        }

        when (position.cubeFace) {
          0 -> return CubePosition(1, 0, position.y, EAST)
          1 -> return CubePosition(3, maxPos, maxPos - position.y, WEST)
          2 -> return CubePosition(1, position.y, maxPos, NORTH)
          3 -> return CubePosition(1, maxPos, maxPos - position.y, WEST)
          4 -> return CubePosition(3, 0, position.y, EAST)
          5 -> return CubePosition(3, position.y, maxPos, NORTH)
        }
      }

      SOUTH -> {
        if (position.y < maxPos) {
          return position.copy(y = position.y + 1)
        }

        when (position.cubeFace) {
          0 -> return CubePosition(2, position.x, 0, SOUTH)
          1 -> return CubePosition(2, maxPos, position.x, WEST)
          2 -> return CubePosition(3, position.x, 0, SOUTH)
          3 -> return CubePosition(5, maxPos, position.x, WEST)
          4 -> return CubePosition(5, position.x, 0, SOUTH)
          5 -> return CubePosition(1, position.x, 0, SOUTH)
        }
      }

      WEST -> {
        if (position.x > 0) {
          return position.copy(x = position.x - 1)
        }

        when (position.cubeFace) {
          0 -> return CubePosition(4, 0, maxPos - position.y, EAST)
          1 -> return CubePosition(0, maxPos, position.y, WEST)
          2 -> return CubePosition(4, position.y, 0, SOUTH)
          3 -> return CubePosition(4, maxPos, position.y, WEST)
          4 -> return CubePosition(0, 0, maxPos - position.y, EAST)
          5 -> return CubePosition(0, position.y, 0, SOUTH)
        }
      }

      NORTH -> {
        if (position.y > 0) {
          return position.copy(y = position.y - 1)
        }

        when (position.cubeFace) {
          0 -> return CubePosition(5, 0, position.x, EAST)
          1 -> return CubePosition(5, position.x, maxPos, NORTH)
          2 -> return CubePosition(0, position.x, maxPos, NORTH)
          3 -> return CubePosition(2, position.x, maxPos, NORTH)
          4 -> return CubePosition(2, 0, position.x, EAST)
          5 -> return CubePosition(4, position.x, maxPos, NORTH)
        }
      }
    }

    throw IllegalStateException("How did you get here?")
  }

  private fun findStart(map: List<String>): Point {
    return Point(
      x = map[0].indexOfFirst { it != ' ' },
      y = 0
    )
  }

  private fun turnLeft(facing: Direction) = values()[(facing.ordinal - 1 + 4) % 4]

  private fun turnRight(facing: Direction) = values()[(facing.ordinal + 1) % 4]

  private fun simpleStep(map: List<String>, curr: Point, facing: Direction): Point {
    val height = map.size
    val width = map[0].length

    val modifier: (Int) -> Point
    when (facing) {
      NORTH -> modifier = { Point(curr.x, (curr.y - it + height) % height) }
      EAST -> modifier = { Point((curr.x + it) % width, curr.y) }
      SOUTH -> modifier = { Point(curr.x, (curr.y + it) % height) }
      WEST -> modifier = { Point((curr.x - it + width) % width, curr.y) }
    }

    var diff = 1
    while (true) {
      val point = modifier(diff)
      when (map[point.y][point.x]) {
        ' ' -> diff++
        '.' -> return point
        '#' -> return curr
      }
    }
  }

  private fun score(x: Int, y: Int, facing: Direction) = 1000 * y + 4 * x + facing.ordinal

  // To make reading it easier, I put the cube sides in a known order (with corner coordinates for scoring later)
  private fun parseCube(input: String): Cube {
    val split = input.split("\n\n")
    val faces = split.take(6).map { parseCubeFace(it) }
    val commands = parseCommands(split.last())
    return Cube(faces, commands)
  }

  private fun parseCubeFace(cube: String): CubeFace {
    val split = cube.splitNewlines()
    val (x, y) = split[0].splitCommas().toIntList()
    return CubeFace(split.drop(1), Point(x, y))
  }

  private fun parseInput(input: String): Setup {
    val (mapStr, commandStr) = input.split("\n\n")
    val map = mapStr.split("\n")
    val commands = parseCommands(commandStr)
    return Setup(map, commands)
  }

  private fun parseCommands(commands: String): List<Command> {
    return Regex("\\d+|[LR]")
      .findAll(commands)
      .map {
        when (it.value) {
          "L" -> Command.TurnLeft
          "R" -> Command.TurnRight
          else -> Command.Move(it.value.toInt())
        }
      }
      .toList()
  }

  private data class CubeFace(val face: List<String>, val corner: Point)

  private data class Cube(val faces: List<CubeFace>, val commands: List<Command>)

  private data class CubePosition(
    val cubeFace: Int,    // The # of the face we're on
    val x: Int,
    val y: Int,
    val facing: Direction, // Northwest always points towards 0,0 on any given face
  )

  private data class Setup(val map: List<String>, val commands: List<Command>)

  private data class Point(val x: Int, val y: Int)

  private enum class Direction { EAST, SOUTH, WEST, NORTH }

  private sealed class Command {
    data class Move(val steps: Int) : Command()
    object TurnLeft : Command()
    object TurnRight : Command()
  }

}
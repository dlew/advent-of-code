import models.XY
import utils.splitNewlines

object Day11 {
  fun part1(input: String) = countOccupiedSeats(stabilize(input, this::part1Rules))

  fun part2(input: String) = countOccupiedSeats(stabilize(input, this::part2Rules))

  private fun stabilize(
    input: String,
    rulesApplier: (grid: Array<Array<State>>) -> Array<Array<State>>,
  ): Array<Array<State>> {
    var grid = parse(input)
    while (true) {
      val next = rulesApplier(grid)
      if (grid.contentDeepEquals(next)) return grid
      grid = next
    }
  }

  private fun part1Rules(grid: Array<Array<State>>): Array<Array<State>> {
    val next = grid.map { it.copyOf() }.toTypedArray()
    grid.forEachIndexed { y, row ->
      row.withIndex()
        .filter { (_, state) -> state != State.GROUND }
        .forEach { (x, state) ->
          val adjacentOccupiedSeats = countAdjacentOccupiedSeats(grid, x, y)
          if (state == State.EMPTY_SEAT && adjacentOccupiedSeats == 0) {
            next[y][x] = State.OCCUPIED_SEAT
          } else if (state == State.OCCUPIED_SEAT && adjacentOccupiedSeats >= 4) {
            next[y][x] = State.EMPTY_SEAT
          }
        }
    }
    return next
  }

  private fun countAdjacentOccupiedSeats(grid: Array<Array<State>>, seatX: Int, seatY: Int): Int {
    return (seatY - 1..seatY + 1)
      .filter { it >= 0 && it < grid.size }
      .sumOf { y ->
        (seatX - 1..seatX + 1)
          .filter { it >= 0 && it < grid[0].size }
          .count { x ->
            (x != seatX || y != seatY) && grid[y][x] == State.OCCUPIED_SEAT
          }
      }
  }

  private fun part2Rules(grid: Array<Array<State>>): Array<Array<State>> {
    val next = grid.map { it.copyOf() }.toTypedArray()
    grid.forEachIndexed { y, row ->
      row.withIndex()
        .filter { (_, state) -> state != State.GROUND }
        .forEach { (x, state) ->
          val visibleOccupiedSeats = countVisibleOccupiedSeats(grid, x, y)
          if (state == State.EMPTY_SEAT && visibleOccupiedSeats == 0) {
            next[y][x] = State.OCCUPIED_SEAT
          } else if (state == State.OCCUPIED_SEAT && visibleOccupiedSeats >= 5) {
            next[y][x] = State.EMPTY_SEAT
          }
        }
    }
    return next
  }

  private val DIRECTIONS = listOf(
    XY(0, -1),
    XY(1, -1),
    XY(1, 0),
    XY(1, 1),
    XY(0, 1),
    XY(-1, 1),
    XY(-1, 0),
    XY(-1, -1),
  )

  private fun countVisibleOccupiedSeats(grid: Array<Array<State>>, seatX: Int, seatY: Int): Int {
    return DIRECTIONS.count { canSeeOccupiedSeat(grid, seatX, seatY, it) }
  }

  private fun canSeeOccupiedSeat(grid: Array<Array<State>>, seatX: Int, seatY: Int, direction: XY): Boolean {
    var x = seatX + direction.x
    var y = seatY + direction.y
    while (y in grid.indices && x in grid[0].indices) {
      when (grid[y][x]) {
        State.OCCUPIED_SEAT -> return true
        State.EMPTY_SEAT -> return false
        else -> {
          x += direction.x
          y += direction.y
        }
      }
    }

    return false // Fell off the grid, we didn't see an occupied seat
  }

  private fun countOccupiedSeats(grid: Array<Array<State>>): Int {
    return grid.sumOf { row ->
      row.count { state -> state == State.OCCUPIED_SEAT }
    }
  }

  private enum class State {
    GROUND,
    EMPTY_SEAT,
    OCCUPIED_SEAT,
  }

  private fun parse(input: String): Array<Array<State>> {
    return input.splitNewlines().map { line ->
      line.map { if (it == '.') State.GROUND else State.EMPTY_SEAT }.toTypedArray()
    }.toTypedArray()
  }
}
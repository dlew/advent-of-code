import models.XYL

object Day13 {
  fun part1(input: String): Long {
    return parse(input)
      .sumOf { it.solutionCost() }
  }

  fun part2(input: String): Long {
    return parse(input)
      .map { it.fixConversionError() }
      .sumOf { it.solutionCost() }
  }

  private data class ClawMachine(val a: XYL, val b: XYL, val prize: XYL)

  private fun ClawMachine.solutionCost(): Long {
    val bPresses = ((prize.y * a.x) - (prize.x * a.y)) / ((a.x * b.y) - (a.y * b.x))
    val aPresses = (prize.x - (bPresses * b.x)) / a.x
    if ((aPresses * a.x + bPresses * b.x != prize.x) || (aPresses * a.y + bPresses * b.y != prize.y)) return 0L
    return aPresses * 3 + bPresses
  }

  private fun ClawMachine.fixConversionError(): ClawMachine {
    val fix = 10000000000000L
    return copy(prize = XYL(prize.x + fix, prize.y + fix))
  }

  private fun parse(input: String): List<ClawMachine> {
    fun xy(x: String, y: String) = XYL(x.toLong(), y.toLong())
    val regex = "X.(\\d+), Y.(\\d+)".toRegex()
    return input.splitNewlines().chunked(4).map { machine ->
      val (ax, ay) = regex.find(machine[0])!!.destructured
      val (bx, by) = regex.find(machine[1])!!.destructured
      val (px, py) = regex.find(machine[2])!!.destructured
      return@map ClawMachine(xy(ax, ay), xy(bx, by), xy(px, py))
    }
  }
}
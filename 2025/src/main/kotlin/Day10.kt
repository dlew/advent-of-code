import utils.splitCommas
import utils.splitNewlines
import utils.splitWhitespace

object Day10 {
  fun part1(input: String) = parse(input).sumOf { pressesToConfigureLights(it) }

  fun part2(input: String) = parse(input).sumOf { pressesToConfigureJoltages(it) }

  private data class Machine(val target: Int, val buttons: List<Int>, val joltages: List<Int>)

  // Key insight is that no button should ever need to be pressed more than once
  private fun pressesToConfigureLights(machine: Machine): Int {
    data class State(val lights: Int, val pressed: Int)

    val queue = ArrayDeque<State>()
    queue.add(State(0, 0))
    while (queue.isNotEmpty()) {
      val state = queue.removeFirst()
      machine.buttons.withIndex().filter { (1 shl it.index) and state.pressed == 0 }.forEach { (index, button) ->
        val next = state.lights xor button
        if (next == machine.target) return state.pressed.countOneBits() + 1
        queue.add(State(next, state.pressed or (1 shl index)))
      }
    }

    throw IllegalStateException("Should've solved by now!")
  }

  private fun pressesToConfigureJoltages(machine: Machine): Int {
    val orderedButtons = machine.buttons.sortedByDescending { it.countOneBits() }
    return solve(machine.joltages.toTypedArray(), orderedButtons.toTypedArray())!!
  }

  // We try every combination of button presses w/ some heuristics to cull possibility space
  private fun solve(
    currJoltages: Array<Int>,
    buttons: Array<Int>,
    buttonIndex: Int = 0,
    totalPresses: Int = 0,
    best: Int = Int.MAX_VALUE,
  ): Int? {
    // Pick the next button
    val button = buttons[buttonIndex]

    // Determine the range at which we can press it
    val maxPresses = minOf(maxPresses(currJoltages, button), best - totalPresses)
    val minPresses = minPresses(currJoltages, buttons, buttonIndex)

    // Can't possibly work, continue
    if (minPresses > maxPresses) return null

    // Try pressing the button anywhere from minPresses to maxPresses times
    val nextJoltages = currJoltages.clone()
    subtractButton(nextJoltages, button, minPresses - 1)
    var currBest = best
    for (presses in minPresses..maxPresses) {
      subtractButton(nextJoltages, button, 1)
      val nextPresses = totalPresses + presses
      val nextButtonIndex = buttonIndex + 1

      // We've found a possible answer - log it
      // (We only test on maxPresses since there's no way it could've been solved before then)
      if (presses == maxPresses && nextJoltages.all { it == 0 }) {
        return minOf(currBest, nextPresses)
      }

      // We haven't solved it, but we've run out of buttons
      if (nextButtonIndex == buttons.size) continue

      // At no point can we do better than the best
      if (nextPresses >= currBest) return currBest

      // Try the next button
      val test = solve(nextJoltages, buttons, nextButtonIndex, nextPresses, currBest)
      if (test != null && test < currBest) {
        currBest = test
      }
    }

    return if (currBest != Int.MAX_VALUE) currBest else null
  }

  private fun minPresses(joltages: Array<Int>, buttons: Array<Int>, buttonIndex: Int): Int {
    val testJoltages = joltages.clone()

    // Find out what happens if you press every remaining button their maximum number of times
    for (i in buttonIndex + 1 until buttons.size) {
      val button = buttons[i]
      val maxPresses = maxPresses(joltages, button)
      subtractButton(testJoltages, button, maxPresses)
    }

    return maxOf(testJoltages.max(), 0)
  }

  private fun maxPresses(joltages: Array<Int>, button: Int): Int {
    var min = Int.MAX_VALUE
    for (index in joltages.indices) {
      if (button and (1 shl index) != 0) {
        min = minOf(min, joltages[index])
      }
    }
    return min
  }

  private fun subtractButton(joltages: Array<Int>, button: Int, presses: Int) {
    if (presses == 0) return

    for (index in joltages.indices) {
      if (button and (1 shl index) != 0) {
        joltages[index] -= presses
      }
    }
  }

  private fun parse(input: String): List<Machine> {
    return input.splitNewlines().map { line ->
      val parts = line.splitWhitespace()

      val target =
        parts.first().dropSides().withIndex().filter { it.value == '#' }.fold(0) { acc, v -> acc or (1 shl v.index) }

      val buttons = parts.drop(1).dropLast(1).map { rawButton ->
        rawButton.dropSides().splitCommas().fold(0) { acc, num -> acc or (1 shl num.toInt()) }
      }

      val joltages = parts.last().dropSides().splitCommas().map { it.toInt() }

      return@map Machine(target, buttons, joltages)
    }
  }

  private fun String.dropSides() = this.drop(1).dropLast(1)
}
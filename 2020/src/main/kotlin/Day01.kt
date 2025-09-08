import utils.splitNewlines
import utils.toIntList

object Day01 {
  fun part1(input: String): Int {
    val numbers = input.splitNewlines().toIntList().toSet()
    for (number in numbers) {
      val target = 2020 - number
      if (numbers.contains(target)) {
        return number * target
      }
    }

    throw IllegalStateException("No answer found - bad input?")
  }

  fun part2(input: String): Int {
    val numbers = input.splitNewlines().toIntList()
    val numberSet = numbers.toSet()
    numbers.forEachIndexed { index, a ->
      numbers.drop(index + 1).forEach { b ->
        val target = 2020 - a - b
        if (numberSet.contains(target)) {
          return a * b * target
        }
      }
    }

    throw IllegalStateException("No answer found - bad input?")
  }
}
import utils.toLongList

object Day09 {
  fun part1(input: String, preambleSize: Int) = findInvalidNumber(parse(input), preambleSize)

  fun part2(input: String, preambleSize: Int): Long {
    val numbers = parse(input)
    val invalidNumber = findInvalidNumber(numbers, preambleSize)

    numbers.forEachIndexed { index, value ->
      var sum = value
      var pos = 0
      while (sum < invalidNumber) {
        pos++
        sum += numbers[index + pos]
      }

      if (sum == invalidNumber && pos != 0) {
        val slice = numbers.slice(index..index + pos)
        return slice.min() + slice.max()
      }
    }

    throw IllegalStateException("Should've solved it by now!")
  }

  private fun findInvalidNumber(numbers: List<Long>, preambleSize: Int): Long {
    val cache = numbers.take(preambleSize).toMutableSet()
    numbers.drop(preambleSize).forEachIndexed { index, target ->
      // Check for validity
      if (cache.none { cache.contains(target - it) }) {
        return target
      }

      // Update cache
      cache.remove(numbers[index])
      cache.add(target)
    }

    throw IllegalStateException("Should've solved it by now!")
  }

  private fun parse(input: String) = input.lines().toLongList()
}
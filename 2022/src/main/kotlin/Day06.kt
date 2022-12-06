object Day06 {

  fun part1(input: String) = findUniqueMarker(input, 4)

  fun part2(input: String) = findUniqueMarker(input, 14)

  private fun findUniqueMarker(input: String, size: Int): Int {
    return input
      .windowed(size)
      .indexOfFirst { it.toSet().size == size } + size
  }

}
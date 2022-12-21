import kotlin.math.abs

object Day20 {

  fun part1(input: String): Long {
    val order = input.splitNewlines().mapIndexed { index, s -> Number(index, s.toLong()) }
    val file = order.toMutableList()
    mix(order, file)
    return groveCoordinates(file)
  }

  fun part2(input: String): Long {
    val order = input.splitNewlines().mapIndexed { index, s -> Number(index, s.toLong() * 811589153L) }
    val file = order.toMutableList()
    repeat(10) { mix(order, file) }
    return groveCoordinates(file)
  }

  private fun groveCoordinates(file: List<Number>): Long {
    val zero = file.indexOfFirst { it.value == 0L }
    return (1000..3000 step 1000).sumOf { file[(zero + it) % file.size].value }
  }

  private fun mix(order: List<Number>, file: MutableList<Number>) {
    val shiftSize = file.size - 1 // When we shift a number around, the file is a wee bit smaller

    for (number in order) {
      val position = file.indexOf(number)
      val shiftMultiplier = if (number.value >= 0) 0 else 1 + (abs(number.value) / shiftSize)
      val newPosition = (position + number.value + (shiftMultiplier * shiftSize)) % shiftSize
      file.removeAt(position)
      file.add(newPosition.toInt(), number)
    }
  }

  // Using this just to avoid duplicate numbers getting mixed up in the order
  private data class Number(val index: Int, val value: Long)

}
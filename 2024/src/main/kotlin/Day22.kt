// Objectively not the simplest solution, but is optimized to be fast
object Day22 {
  fun part1(input: String): Long {
    return input.splitNewlines()
      .map {
        var secret = it.toInt()
        repeat(2000) { secret = secret.iterate() }
        return@map secret.toLong()
      }
      .sum()
  }

  fun part2(input: String): Int {
    var pattern = 0 // Packed int representing "pattern" of the last 4 diffs, as numbers -9..9 (each stored in 5 bits)
    val size = 599338 // The max value a `pattern` can have (if "9,0,0,0")
    val priceArray = IntArray(size)
    val used = BooleanArray(size)

    input.splitNewlines().forEach { line ->
      used.fill(false)
      var num = line.toInt()
      var lastPrice = num % 10
      repeat(2000) {
        num = num.iterate()
        val price = num % 10
        pattern = ((pattern shl 5) + price - lastPrice + 9) and 0xFFFFF

        if (it > 2) {
          if (!used[pattern]) {
            priceArray[pattern] += price
            used[pattern] = true
          }
        }
        lastPrice = price
      }
    }
    return priceArray.max()
  }

  private fun Int.iterate(): Int {
    val step1 = this shl 6 xor this and 0xFFFFFF
    val step2 = step1 shr 5 xor step1 // Don't need to mask because we shifted right
    return step2 shl 11 xor step2 and 0xFFFFFF
  }
}
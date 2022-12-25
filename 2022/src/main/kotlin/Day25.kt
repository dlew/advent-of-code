import kotlin.math.pow

object Day25 {

  fun part1(input: String) = numToSnafu(input.splitNewlines().sumOf(::snafuToNum))

  private fun snafuToNum(snafu: String): Long {
    return snafu
      .reversed()
      .withIndex()
      .sumOf { (index, char) ->
        5.0.pow(index).toLong() * snafuCharToNum(char)
      }
  }

  private fun snafuCharToNum(a: Char) = when (a) {
    '-' -> -1L
    '=' -> -2L
    else -> a.digitToInt().toLong()
  }

  private fun numToSnafu(n: Long): String {
    var curr = n
    var carry = 0
    val snafu = mutableListOf<Int>()
    while (curr != 0L) {
      var result = carry + (curr % 5).toInt()
      if (result < 3) {
        carry = 0
      }
      else {
        carry = 1
        result -= 5
      }
      snafu.add(result)
      curr /= 5
    }

    if (carry == 1) {
      snafu.add(1)
    }

    return snafu
      .reversed()
      .map(::digitToSnafu)
      .joinToString("")
  }

  private fun digitToSnafu(n: Int) = when (n) {
    -2 -> '='
    -1 -> '-'
    else -> n.digitToChar()
  }

}
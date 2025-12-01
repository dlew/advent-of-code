import utils.splitNewlines

object Day01 {
  fun part1(input: String): Int {
    var dial = 50
    var zeroes = 0

    input.splitNewlines().forEach { line ->
      val count = line.drop(1).toInt()
      dial = (dial + if (line[0] == 'R') count else -count) % 100
      if (dial == 0) zeroes++
    }

    return zeroes
  }

  fun part2(input: String): Int {
    var dial = 50
    var zeroes = 0

    input.splitNewlines().forEach { line ->
      val before = dial
      val count = line.drop(1).toInt()
      dial += if (line[0] == 'R') count else -count

      if (dial > 99) {
        zeroes += dial / 100
        dial %= 100
      } else if (dial <= 0) {
        zeroes += 1 - (dial / 100)
        if (before == 0) zeroes-- // Avoid double-counting when *starting* at 0
        dial = ((dial % 100) + 100) % 100
      }
    }

    return zeroes
  }
}
import com.google.common.math.IntMath.pow

// Note: I coded the program's calculation directly; this will not solve the general case
object Day17 {
  fun part1(): String {
    val output = mutableListOf<Int>()
    var a = 30344604L
    while (a != 0L) {
      output.add(output(a))
      a /= 8
    }
    return output.joinToString(",")
  }

  /**
   * Key insight: we know how the program must end (with `a` == 0 and an output of 0).
   *
   * Therefore, we can calculate what `a` could have been to get that output (since there's
   * only so many values of `a` that would result in `a / 8 == 0`).
   *
   * We can apply this reasoning inductively, building up the output in reverse, finding all
   * possible `a` values along the way that match the output
   */
  fun part2(): Long {
    val program = listOf(2, 4, 1, 1, 7, 5, 1, 5, 4, 5, 0, 3, 5, 5, 3, 0)
    var possibleAValues = listOf(0L)
    program.reversed().forEach { target ->
      possibleAValues = possibleAValues
        .flatMap { possibleNextStarts(it) }
        .filter { a -> output(a) == target }
    }
    return possibleAValues.min()
  }

  // Returns the output command from running the loop once
  // (Determined by studying the bytecode of my input)
  private fun output(a: Long): Int {
    val b = (a % 8) xor 1
    val c = a / pow(2, b.toInt())
    return (((b xor 5) xor c) % 8).toInt()
  }

  // Finds all possible values of `a` that would result in the next loop having `a == target`
  private fun possibleNextStarts(target: Long): List<Long> {
    val min = target * 8
    return (min..min + 7).toList()
  }
}
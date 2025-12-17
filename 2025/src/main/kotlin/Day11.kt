import utils.splitNewlines

object Day11 {
  fun part1(input: String) = countPaths(parse(input), "you", "out")

  fun part2(input: String): Long {
    val devices = parse(input)

    // All valid paths go from svr -> fft -> dac -> out, so if we know how many paths there are
    // from each key node to the next, we can simply calculate the total paths from that
    val svrToFft = countPaths(devices, "svr", "fft")
    val fftToDac = countPaths(devices, "fft", "dac")
    val dacToOut = countPaths(devices, "dac", "out")

    return svrToFft * fftToDac * dacToOut
  }

  private fun countPaths(
    devices: Map<String, Set<String>>,
    start: String,
    end: String,
    memo: MutableMap<String, Long> = mutableMapOf(),
  ): Long {
    return memo.getOrPut(start) {
      when (start) {
        end -> 1
        "out" -> 0 // Need terminal case, but you can make `end` == "out" to find that path
        else -> devices[start]!!.sumOf { countPaths(devices, it, end, memo) }
      }
    }
  }

  private fun parse(input: String): Map<String, Set<String>> {
    return input.splitNewlines().associate {
      val split = it.split(Regex(":? "))
      split.first() to split.drop(1).toSet()
    }
  }
}
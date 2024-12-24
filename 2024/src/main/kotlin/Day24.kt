object Day24 {
  fun part1(input: String): Long {
    val scenario = parse(input)
    val wires = run(scenario)
    return getValue(wires)
  }

  /**
   * My solution uses the fact that the gates are a minimal set to solve addition.
   *
   * That means there are:
   * 44 initial XOR gates (x00 XOR y00 ... x44 XOR y44)
   * 45 ending XOR gates (a XOR b -> z00 ... y XOR z -> z44)
   * 44 initial AND gates (x00 AND y00 ... x44 AND y44)
   * 45 ending AND gates (a AND b, which is used for z02 through z45, to figure out carryover)
   * 44 OR gates (for z02 through z45, to chain together carryover cases)
   *
   * In a condensed form, the setup looks like this:
   *
   * xor00 == x00 XOR y00 -> ...
   * and00 == x00 AND y00 -> ...
   *
   * z00 == xor00
   * z01 == xor01 XOR [and00] -> a
   * z02 == xor02 XOR [and01 OR (xor01 AND a)] -> b
   * z03 == xor03 XOR [and02 OR (xor02 AND b)] -> c
   * z04 == xor04 XOR [and03 OR (xor03 AND c)] -> d
   * ...
   * z45 == and44 or [xor44 AND chain]
   *
   * From the above, you can logic out when an impossible situation
   * has arisen, and figure out what must be in its place.
   */
  fun part2(input: String): String {
    val scenario = parse(input)

    // Note: theoretically there could be a chain reaction of issues,
    // but in my input issues are always localized to two gates swapping,
    // so I don't need to store swap state in the gates themselves
    val gates = scenario.gates
    val swaps = mutableListOf<String>()

    // Skip the first gate, in my input it's correct
    for (it in (1..<scenario.wires.size / 2)) {
      val num = it.toString().padStart(2, '0')

      // Bad case #1: every gate leading to z## must be an XOR
      val zGate = gates.first { it.out == "z$num" }
      if (zGate.op != Operation.XOR) {
        // Find XORs that don't make sense
        val candidates = gates.filter {
          !it.out.startsWith("z")
              && !it.left.startsWith('x')
              && !it.right.startsWith('x')
              && it.op == Operation.XOR
        }

        // Figure out which one matches with the zGate we're confused about
        val hit = candidates.first { candidate ->
          val left = gates.first { it.out == candidate.left }
          val right = gates.first { it.out == candidate.right }
          return@first left.left.endsWith(num) || right.left.endsWith(num)
        }

        swaps.add(zGate.out)
        swaps.add(hit.out)

        // Admittedly, there could be further problems, but not in my input
        continue
      }

      // Bad case #2: x## XOR y## must lead to one of z##'s inputs
      val xorGate = gates.first { it.left.endsWith(num) && it.op == Operation.XOR }
      if (zGate.left != xorGate.out && zGate.right != xorGate.out) {
        // There could be other mistakes here, but this is what I found in my input
        val andGate = gates.first { (it.out == zGate.left || it.out == zGate.right) && it.op == Operation.AND }
        swaps.add(xorGate.out)
        swaps.add(andGate.out)
      }

      // There are other possible bad logic situations, but these are the only
      // ones I found in my input
    }

    return swaps.sorted().joinToString(",")
  }

  private fun run(scenario: Scenario, swaps: Map<String, String> = emptyMap()): Map<String, Boolean> {
    val wires = scenario.wires.toMutableMap()
    var gates = scenario.gates
    while (gates.isNotEmpty()) {
      val nextGates = mutableListOf<Gate>()
      gates.forEach { gate ->
        val out = swaps[gate.out] ?: gate.out
        if (out !in wires && gate.left in wires && gate.right in wires) {
          val left = wires[gate.left]!!
          val right = wires[gate.right]!!
          wires[out] = when (gate.op) {
            Operation.AND -> left && right
            Operation.OR -> left || right
            Operation.XOR -> left != right
          }
        } else {
          nextGates.add(gate)
        }
      }
      gates = nextGates
    }
    return wires
  }

  private fun getValue(wires: Map<String, Boolean>): Long {
    return wires
      .filter { it.key.startsWith('z') }
      .mapKeys { it.key.drop(1).toInt() }
      .toList()
      .sortedByDescending { it.first }
      .map { it.second }
      .fold(0L) { acc, value -> (acc shl 1) + (if (value) 1 else 0) }
  }

  private data class Scenario(val wires: Map<String, Boolean>, val gates: List<Gate>)

  private data class Gate(val left: String, val op: Operation, val right: String, val out: String)

  private enum class Operation {
    AND, OR, XOR
  }

  private fun parse(input: String): Scenario {
    val (initial, rawGates) = input.split("\n\n")

    val wires = initial.splitNewlines().associate {
      val (name, value) = it.split(": ")
      return@associate name to (value == "1")
    }

    val regex = Regex("(\\w+) (\\w+) (\\w+) -> (\\w+)")
    val gates = rawGates.splitNewlines().map {
      val (left, op, right, out) = regex.matchEntire(it)!!.destructured
      return@map Gate(left, Operation.valueOf(op), right, out)
    }

    return Scenario(wires, gates)
  }
}
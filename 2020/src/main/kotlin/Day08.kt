import utils.splitNewlines

object Day08 {
  fun part1(input: String) = run(parse(input)).accumulator

  fun part2(input: String): Int {
    val ops = parse(input)

    for (pos in ops.indices) {
      val newOps = ops.toMutableList()
      val op = ops[pos]

      when (op.type) {
        OpType.ACC -> continue
        OpType.JMP -> newOps[pos] = op.copy(type = OpType.NOP)
        OpType.NOP -> newOps[pos] = op.copy(type = OpType.JMP)
      }

      val result = run(newOps)
      if (result.halts) {
        return result.accumulator
      }
    }

    throw IllegalStateException("Should've found an answer!")
  }

  private fun run(ops: List<Op>): Result {
    val seen = mutableSetOf<Int>()

    var accumulator = 0
    var pos = 0
    while (pos < ops.size) {
      if (pos in seen) {
        return Result(halts = false, accumulator = accumulator)
      }

      seen.add(pos)
      val op = ops[pos]
      when (op.type) {
        OpType.ACC -> {
          accumulator += op.value
          pos++
        }

        OpType.JMP -> pos += op.value
        OpType.NOP -> pos++
      }
    }

    return Result(halts = true, accumulator = accumulator)
  }

  private data class Result(val halts: Boolean, val accumulator: Int)

  private enum class OpType { ACC, JMP, NOP }

  private data class Op(val type: OpType, val value: Int)

  private fun parse(input: String): List<Op> {
    return input.splitNewlines().map { line ->
      val (op, value) = line.split(" ")
      val opType = when (op) {
        "acc" -> OpType.ACC
        "jmp" -> OpType.JMP
        "nop" -> OpType.NOP
        else -> throw IllegalArgumentException("Unknown operation $op")
      }
      Op(opType, value.toInt())
    }
  }
}
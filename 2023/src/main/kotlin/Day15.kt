object Day15 {

  fun part1(input: String) = input.splitCommas().sumOf(::hash)

  fun part2(input: String): Int {
    val operations = parse(input)
    val boxes = Array(256) { mutableListOf<Lens>() }

    operations.forEach { op ->
      val box = boxes[hash(op.label)]
      when (op) {
        is Operation.Put -> {
          val newLens = Lens(op.label, op.focalLength)
          val existingLens = box.indexOfFirst { it.label == op.label }
          if (existingLens == -1) {
            box.add(newLens)
          } else {
            box[existingLens] = newLens
          }
        }

        is Operation.Remove -> box.removeIf { it.label == op.label }
      }
    }

    return boxes.withIndex().sumOf { (box, lenses) ->
      lenses.withIndex().sumOf { (slot, lens) ->
        (box + 1) * (slot + 1) * lens.focalLength
      }
    }
  }

  private fun hash(input: String) = input.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }

  private sealed class Operation {
    abstract val label: String

    data class Put(override val label: String, val focalLength: Int) : Operation()

    data class Remove(override val label: String) : Operation()
  }

  private data class Lens(val label: String, val focalLength: Int)

  private fun parse(input: String): List<Operation> {
    val regex = Regex("(\\w+)([=-])(\\d+)?")
    return input.splitCommas().map {
      val match = regex.matchEntire(it)!!
      when (match.groupValues[2]) {
        "-" -> Operation.Remove(match.groupValues[1])
        else -> Operation.Put(match.groupValues[1], match.groupValues[3].toInt())
      }
    }
  }

}
object Day05 {

  data class Almanac(val seeds: List<Long>, val steps: List<List<Transformer>>)

  data class Transformer(val range: LongRange, val transform: Long)

  fun part1(input: String): Long {
    val almanac = parseAlmanac(input)
    return almanac.seeds.minOf { seed ->
      almanac.steps.fold(seed) { value, transfomers ->
        val transformer = transfomers.find { value in it.range }
        return@fold if (transformer != null) value + transformer.transform else value
      }
    }
  }

  fun part2(input: String): Long {
    val almanac = parseAlmanac(input)
    val initialRanges = almanac.seeds.chunked(2).map { (start, len) -> start..<start + len }

    // For each step, convert all set of ranges into new set of ranges
    return almanac.steps
      .map { transformers -> transformers.sortedBy { it.range.first } }
      .fold(initialRanges) { ranges, transformers ->
        ranges.flatMap { range ->
          val newRanges = mutableListOf<LongRange>()
          var curr = range.first
          var stepIndex = 0

          // Step through all transformers, creating new ranges when matching
          while (curr <= range.last && stepIndex < transformers.size) {
            val transformer = transformers[stepIndex]
            if (curr in transformer.range) {
              // Map this range onto a new range
              val end = minOf(range.last, transformer.range.last)
              val length = end - curr
              val newStart = curr + transformer.transform
              newRanges.add(newStart..newStart + length)
              curr = end + 1
              stepIndex++
            } else if (range.last < transformer.range.first) {
              // We end before the next possible transformer range
              break
            } else if (curr < transformer.range.first) {
              // We're not in transformer range, but could step up to be within it
              newRanges.add(curr..<transformer.range.first)
              curr = transformer.range.first
            } else {
              // We can't match with this transformer, but might with the next
              stepIndex++
            }
          }

          // Leftover range converted 1:1 with no transform
          if (curr <= range.last) {
            newRanges.add(curr..range.last)
          }

          return@flatMap newRanges
        }
      }
      .minOf { it.first }
  }

  private fun parseAlmanac(input: String): Almanac {
    val lines = input.splitNewlines()

    val seeds = lines[0].drop(7).splitWhitespace().toLongList()

    val steps = mutableListOf<List<Transformer>>()
    var transformers = mutableListOf<Transformer>()
    lines.drop(2).forEach { line ->
      if (line.isEmpty()) {
        steps.add(transformers)
        transformers = mutableListOf()
      } else if (line[0].isDigit()) {
        val (destinationRangeStart, sourceRangeStart, rangeLength) = line.splitWhitespace().toLongList()
        transformers.add(
          Transformer(
            range = sourceRangeStart..<sourceRangeStart + rangeLength,
            transform = destinationRangeStart - sourceRangeStart
          )
        )
      }
    }

    steps.add(transformers)

    return Almanac(seeds, steps)
  }
}
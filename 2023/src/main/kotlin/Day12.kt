object Day12 {

  fun part1(input: String) = parseInput(input).sumOf(::numConfigurations)

  fun part2(input: String) = parseInput(input)
    .map { it.unfold() }
    .sumOf(::numConfigurations)

  private enum class State { OPERATIONAL, DAMAGED, UNKNOWN }

  private data class Record(val springs: List<State>, val groups: List<Int>)

  private val memos = mutableMapOf<Record, Long>()

  private fun numConfigurations(record: Record): Long {
    memos[record]?.let { return it }

    if (record.groups.isEmpty()) {
      return if (State.DAMAGED !in record.springs) 1 else 0
    }

    // Find all possible places we could put the next damage group within the record
    val spots = possibleSpotsForDamage(record)

    // Filter out any places where the next space is DAMAGED (since that would meld with the current group otherwise)
    val filteredSpots = spots.filter {
      val next = it.last + 1
      return@filter if (next in record.springs.indices) record.springs[next] != State.DAMAGED else true
    }

    // Create a new set of records by tossing out the consumed springs & groups
    val nextSprings = filteredSpots
      .map { record.springs.subList(minOf(it.last + 2, record.springs.size), record.springs.size) }
    val nextGroups = record.groups.subList(1, record.groups.size)

    val result = nextSprings
      .map { Record(it, nextGroups) }
      .sumOf { numConfigurations(it) }
    memos[record] = result
    return result
  }

  private fun possibleSpotsForDamage(record: Record): List<IntRange> {
    val springs = record.springs
    val nextGroupSize = record.groups[0]
    val totalDamageRemaining = record.groups.sum()

    val spots = mutableListOf<IntRange>()

    var unknowns = 0
    var damage = 0
    springs.forEachIndexed { index, state ->
      when (state) {
        State.OPERATIONAL -> {
          if (damage != 0) {
            return spots
          }

          unknowns = 0
        }

        State.DAMAGED -> {
          unknowns++
          damage++
        }

        State.UNKNOWN -> {
          unknowns++

          // If we've seen damage, we MUST continue adding damage
          if (damage != 0) {
            damage++
          }
        }
      }

      if (unknowns >= nextGroupSize) {
        spots.add(index - nextGroupSize + 1..index)
      }

      if (damage == nextGroupSize) {
        return spots
      }

      // Heuristic exit if there's no way we could fill up the remaining spots with damage
      if (springs.size - index < totalDamageRemaining - nextGroupSize) {
        return spots
      }
    }

    return spots
  }

  private fun parseInput(input: String): List<Record> {
    return input.splitNewlines().map { line ->
      val (springs, groups) = line.splitWhitespace()
      Record(
        springs = springs.map {
          when (it) {
            '.' -> State.OPERATIONAL
            '#' -> State.DAMAGED
            else -> State.UNKNOWN
          }
        },
        groups = groups.splitCommas().toIntList()
      )
    }
  }

  private fun Record.unfold() = Record(
    Array(5) { listOf(State.UNKNOWN) + springs }.flatMap { it }.drop(1),
    Array(5) { groups }.flatMap { it }
  )
}
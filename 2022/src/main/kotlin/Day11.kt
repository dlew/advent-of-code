import Day11.Operand.Value

object Day11 {

  fun part1(input: String): Long {
    val monkeys = parseMonkeys(input)
    repeat(20) {
      simulateRound(monkeys) { it / 3L }
    }
    return calculateMonkeyBusiness(monkeys)
  }

  fun part2(input: String): Long {
    val monkeys = parseMonkeys(input)
    val divisorProduct = monkeys.map { it.divisor }.fold(1L, Long::times)
    repeat(10_000) {
      simulateRound(monkeys) { it % divisorProduct }
    }
    return calculateMonkeyBusiness(monkeys)
  }

  private fun simulateRound(monkeys: List<Monkey>, worryReducer: (Long) -> Long) {
    monkeys.forEach { monkey ->
      while (monkey.items.isNotEmpty()) {
        val itemWorryLevel = monkey.items.removeFirst()
        val newWorryLevel = worryReducer(monkey.worry(itemWorryLevel))
        val passTo = monkey.test(newWorryLevel)
        monkeys[passTo].items.add(newWorryLevel)
        monkey.numItemsInspected++
      }
    }
  }

  private fun calculateMonkeyBusiness(monkeys: List<Monkey>): Long {
    return monkeys
      .map { it.numItemsInspected }
      .sortedDescending()
      .take(2)
      .fold(1L, Long::times)
  }

  private fun parseMonkeys(input: String): List<Monkey> {
    return input.split("\n\n")
      .map { monkeyDefinition ->
        val monkeyLines = monkeyDefinition.splitNewlines()
        val operationSplit = monkeyLines[2].drop(19).splitWhitespace()

        return@map Monkey(
          items = monkeyLines[1].drop(18).splitCommas().map { it.trim().toLong() }.toMutableList(),
          function = if (operationSplit[1] == "+") Long::plus else Long::times,
          operand = operationSplit[2].parseOperand(),
          divisor = monkeyLines[3].drop(21).toLong(),
          ifTrue = monkeyLines[4].drop(29).toInt(),
          ifFalse = monkeyLines[5].drop(30).toInt()
        )
      }
  }

  private fun String.parseOperand() = if (this == "old") Operand.Old else Value(this.toLong())

  private data class Monkey(
    var items: MutableList<Long>,
    val function: (Long, Long) -> Long,
    val operand: Operand,
    val divisor: Long,
    val ifTrue: Int,
    val ifFalse: Int,
  ) {
    var numItemsInspected: Long = 0

    fun worry(old: Long) = function(old, if (operand is Value) operand.value else old)

    fun test(item: Long) = if (item % divisor == 0L) ifTrue else ifFalse
  }

  private sealed class Operand {
    data class Value(val value: Long) : Operand()
    object Old : Operand()
  }

}
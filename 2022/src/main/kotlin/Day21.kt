import Day21.Operation.*

object Day21 {

  fun part1(input: String): Long {
    val monkeys = parseMonkeys(input).associateBy { it.name }
    return getYell("root", monkeys)
  }

  fun part2(input: String): Long {
    val monkeyMap = parseMonkeys(input).filter { it.name != "humn" }.associateBy { it.name }

    val yellMap = mutableMapOf<String, Long>()
    fillYellMap("root", monkeyMap, yellMap)

    val root = monkeyMap["root"] as Monkey.OperationMonkey
    if (root.b in yellMap) {
      return findHumanYell(root.a, monkeyMap, yellMap, yellMap[root.b]!!)
    }
    else {
      return findHumanYell(root.b, monkeyMap, yellMap, yellMap[root.a]!!)
    }
  }

  private fun findHumanYell(
    monkeyName: String,
    monkeyMap: Map<String, Monkey>,
    yellMap: Map<String, Long>,
    result: Long
  ): Long {
    if (monkeyName == "humn") {
      return result
    }

    val monkey = monkeyMap[monkeyName] as Monkey.OperationMonkey
    if (monkey.a in yellMap) {
      val yellA = yellMap[monkey.a]!!
      val resultUndone = monkey.reverseForB(yellA, result)
      return findHumanYell(monkey.b, monkeyMap, yellMap, resultUndone)
    }
    else {
      val yellB = yellMap[monkey.b]!!
      val resultUndone = monkey.reverseForA(yellB, result)
      return findHumanYell(monkey.a, monkeyMap, yellMap, resultUndone)
    }
  }

  // Given a full set of data, what does this monkey yell?
  private fun getYell(
    monkeyName: String,
    monkeyMap: Map<String, Monkey>,
    yellMap: MutableMap<String, Long> = mutableMapOf(),
  ): Long {
    return yellMap.getOrPut(monkeyName) {
      when (val monkey = monkeyMap[monkeyName]!!) {
        is Monkey.ValueMonkey -> monkey.value
        is Monkey.OperationMonkey -> monkey.operation(
          getYell(monkey.a, monkeyMap, yellMap),
          getYell(monkey.b, monkeyMap, yellMap)
        )
      }
    }
  }

  // Given a set of data minus humn, how much can we pre-calculate the monkeys yelling?
  private fun fillYellMap(
    monkeyName: String,
    monkeyMap: Map<String, Monkey>,
    yellMap: MutableMap<String, Long> = mutableMapOf(),
  ): Long? {
    if (monkeyName !in yellMap) {
      when (val monkey = monkeyMap[monkeyName] ?: return null) {
        is Monkey.ValueMonkey -> yellMap[monkeyName] = monkey.value

        is Monkey.OperationMonkey -> {
          val yellA = fillYellMap(monkey.a, monkeyMap, yellMap)
          val yellB = fillYellMap(monkey.b, monkeyMap, yellMap)
          yellMap[monkeyName] = monkey.operation(
            yellA ?: return null,
            yellB ?: return null
          )
        }
      }
    }

    return yellMap[monkeyName]
  }

  private val VALUE_MONKEY = Regex("(\\w{4}): (\\d+)")
  private val OPERATION_MONKEY = Regex("(\\w{4}): (\\w{4}) (.) (\\w{4})")

  private fun parseMonkeys(input: String): List<Monkey> {
    return input.splitNewlines()
      .map {
        val valueMatch = VALUE_MONKEY.matchEntire(it)
        if (valueMatch != null) {
          return@map Monkey.ValueMonkey(valueMatch.groupValues[1], valueMatch.groupValues[2].toLong())
        }

        val (name, a, opStr, b) = OPERATION_MONKEY.matchEntire(it)!!.destructured
        val operation = when (opStr) {
          "+" -> PLUS
          "-" -> MINUS
          "*" -> TIMES
          "/" -> DIV
          else -> throw IllegalArgumentException("Unknown operation: $opStr")
        }
        return@map Monkey.OperationMonkey(name, a, b, operation)
      }
  }

  enum class Operation {
    PLUS,
    MINUS,
    TIMES,
    DIV
  }

  sealed class Monkey {
    abstract val name: String

    data class ValueMonkey(override val name: String, val value: Long) : Monkey()

    data class OperationMonkey(
      override val name: String,
      val a: String,
      val b: String,
      val operation: Operation,
    ) : Monkey() {
      fun operation(a: Long, b: Long): Long {
        return when (operation) {
          PLUS -> a + b
          MINUS -> a - b
          TIMES -> a * b
          DIV -> a / b
        }
      }

      fun reverseForA(b: Long, result: Long): Long {
        return when (operation) {
          PLUS -> result - b
          MINUS -> result + b
          TIMES -> result / b
          DIV -> result * b
        }
      }

      fun reverseForB(a: Long, result: Long): Long {
        return when (operation) {
          PLUS -> result - a
          MINUS -> a - result
          TIMES -> result / a
          DIV -> a / result
        }
      }
    }
  }

}
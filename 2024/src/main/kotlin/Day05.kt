private typealias Rule = Pair<Int, Int>
private typealias Manual = List<Int>

object Day05 {
  fun part1(input: String): Int {
    val (rules, manuals) = parse(input)
    return manuals
      .filter { it.isCorrectlyOrdered(rules) }
      .sumOf { it.middle() }
  }

  fun part2(input: String): Int {
    val (rules, manuals) = parse(input)
    return manuals
      .filterNot { it.isCorrectlyOrdered(rules) }
      .map { it.fixOrdering(rules) }
      .sumOf { it.middle() }
  }

  private fun Manual.isCorrectlyOrdered(rules: List<Rule>): Boolean {
    return rules.all { (left, right) ->
      val leftIndex = indexOf(left)
      val rightIndex = indexOf(right)
      return@all leftIndex == -1 || rightIndex == -1 || leftIndex < rightIndex
    }
  }

  private fun Manual.fixOrdering(rules: List<Rule>): Manual {
    var manual = this
    while (!manual.isCorrectlyOrdered(rules)) {
      manual = manual.fixOrderingPass(rules)
    }
    return manual
  }

  private fun Manual.fixOrderingPass(rules: List<Rule>): Manual {
    val manual = toMutableList()
    rules.forEach { (left, right) ->
      val leftIndex = manual.indexOf(left)
      val rightIndex = manual.indexOf(right)
      if (leftIndex != -1 && rightIndex != -1 && leftIndex > rightIndex) {
        manual.add(rightIndex, manual.removeAt(leftIndex))
      }
    }
    return manual
  }

  private fun Manual.middle() = get(size / 2)

  private fun parse(input: String): Pair<List<Rule>, List<Manual>> {
    val rules = mutableListOf<Rule>()
    val manuals = mutableListOf<Manual>()
    var parsingRules = true
    input.splitNewlines().forEach { line ->
      when {
        line.isEmpty() -> parsingRules = false

        parsingRules -> {
          val (left, right) = line.split("|")
          rules.add(Pair(left.toInt(), right.toInt()))
        }

        else -> manuals.add(line.splitCommas().toIntList())
      }
    }
    return rules to manuals
  }
}


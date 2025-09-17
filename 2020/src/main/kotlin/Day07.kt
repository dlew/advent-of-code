import utils.splitNewlines

object Day07 {
  fun part1(input: String): Int {
    val rules = parse(input)
    return rules.keys.count { containsShinyGold(it, rules) }
  }

  fun part2(input: String) = countBags("shiny gold", parse(input)) - 1

  private fun containsShinyGold(color: String, rules: Map<String, Map<String, Int>>): Boolean {
    val bagRules = rules[color]!!
    if ("shiny gold" in bagRules) return true
    return bagRules.any { containsShinyGold(it.key, rules) }
  }

  private fun countBags(color: String, rules: Map<String, Map<String, Int>>): Int {
    val bagRules = rules[color]!!
    return 1 + bagRules.entries.sumOf { (color, count) -> count * countBags(color, rules) }
  }

  val RULE_PATTERN  = Regex("(\\w+ \\w+) bags contain (.+)\\.")
  val BAG_PATTERN  = Regex("(\\d+) (\\w+ \\w+) bags?")

  private fun parse(input: String): Map<String, Map<String, Int>> {
    return input.splitNewlines().associate { line ->
      val (color, container) = RULE_PATTERN.matchEntire(line)!!.destructured
      if (container == "no other bags") {
        return@associate  color to emptyMap()
      }

      val contains = container.split(", ").associate {
        val (count, color) = BAG_PATTERN.matchEntire(it)!!.destructured
        color to count.toInt()
      }
      return@associate color to contains
    }
  }
}
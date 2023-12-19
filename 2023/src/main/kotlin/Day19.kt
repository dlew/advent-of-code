import Day19.Rule.Outcome
import java.util.regex.Pattern

private enum class Category { X, M, A, S }

private typealias Part = Map<Category, Int>

private typealias PartRange = Map<Category, IntRange>

object Day19 {

  fun part1(input: String): Int {
    val system = parse(input)
    val acceptedParts = system.parts.filter { part -> processPart(system.workflows, part) }
    return acceptedParts.sumOf { it.values.sum() }
  }

  private fun processPart(workflows: Map<String, Workflow>, part: Part): Boolean {
    var workflow = workflows["in"]!!
    while (true) {
      when (val next = processWorkflow(workflow, part)) {
        is Rule.Jump -> workflow = workflows[next.workflow]!!
        is Rule.Result -> return next.accepted
      }
    }
  }

  private fun processWorkflow(workflow: Workflow, part: Part): Outcome {
    workflow.rules.forEach { rule ->
      if (rule is Outcome) {
        return rule
      }

      if (rule is Rule.ConditionalJump && matchesRule(rule, part)) {
        return rule.outcome
      }
    }

    throw IllegalStateException("Should always match last rule!")
  }

  private fun matchesRule(rule: Rule.ConditionalJump, part: Part): Boolean {
    val rating = part[rule.category]!!
    return when (rule.comparison) {
      Comparison.LESS_THAN -> rating < rule.value
      Comparison.GREATER_THAN -> rating > rule.value
    }
  }

  //////////////////////////////////////////////////////

  fun part2(input: String): Long {
    val system = parse(input)
    val initialPart = Category.entries.associateWith { (1..4000) }
    val initialWorkflow = system.workflows["in"]!!
    return processRange(system.workflows, initialWorkflow, initialPart)
  }

  private fun processRange(workflows: Map<String, Workflow>, workflow: Workflow, partRange: PartRange): Long {
    when (val rule = workflow.rules.first()) {
      is Rule.ConditionalJump -> {
        val currRange = partRange[rule.category]!!

        val trueRange: IntRange
        val falseRange: IntRange
        when (rule.comparison) {
          Comparison.LESS_THAN -> {
            trueRange = (currRange.first..<rule.value)
            falseRange = (rule.value..currRange.last)
          }

          Comparison.GREATER_THAN -> {
            falseRange = (currRange.first..rule.value)
            trueRange = (rule.value + 1..currRange.last)
          }
        }

        val truePartRange = partRange + mapOf(rule.category to trueRange)
        val falsePartRange = partRange + mapOf(rule.category to falseRange)

        val trueOutcome = when (rule.outcome) {
          is Rule.Jump -> processRange(workflows, workflows[rule.outcome.workflow]!!, truePartRange)
          is Rule.Result -> if (rule.outcome.accepted) truePartRange.numCombinations() else 0
        }

        val falseOutcome =
          processRange(workflows, workflow.copy(rules = workflow.rules.drop(1)), falsePartRange)

        return trueOutcome + falseOutcome
      }

      is Rule.Jump -> return processRange(workflows, workflows[rule.workflow]!!, partRange)

      is Rule.Result -> return if (rule.accepted) partRange.numCombinations() else 0
    }
  }

  private fun PartRange.numCombinations() = this.values.map { it.last - it.first + 1L }.reduce(Long::times)

  private enum class Comparison { LESS_THAN, GREATER_THAN }

  private sealed class Rule {
    data class ConditionalJump(
      val category: Category,
      val comparison: Comparison,
      val value: Int,
      val outcome: Outcome
    ) : Rule()

    sealed class Outcome : Rule()
    data class Jump(val workflow: String) : Outcome()
    data class Result(val accepted: Boolean) : Outcome()
  }

  private data class Workflow(val name: String, val rules: List<Rule>)

  private data class System(val workflows: Map<String, Workflow>, val parts: List<Part>)

  //////////////////////////////////////////////////////

  private fun parse(input: String): System {
    val (workflows, parts) = input.split(Pattern.compile("\\r?\\n\\r?\\n"))
    return System(
      workflows.splitNewlines().map { parseWorkflow(it) }.associateBy { it.name },
      parts.splitNewlines().map { parsePart(it) }
    )
  }

  private val WORKFLOW_REGEX = Regex("(\\w+)\\{(.*)}")
  private val RULE_REGEX = Regex("([xmas])([<>])(\\d+):(\\w+)")
  private val RATING_REGEX = Regex("([xmas])=(\\d+)")

  private fun parseWorkflow(workflow: String): Workflow {
    val workflowMatch = WORKFLOW_REGEX.matchEntire(workflow)!!
    return Workflow(
      name = workflowMatch.groupValues[1],
      rules = workflowMatch.groupValues[2].splitCommas().map {
        when (it) {
          "A" -> Rule.Result(true)
          "R" -> Rule.Result(false)
          else -> {
            val ruleMatch = RULE_REGEX.matchEntire(it)
            if (ruleMatch == null) {
              Rule.Jump(it)
            } else {
              Rule.ConditionalJump(
                category = Category.valueOf(ruleMatch.groupValues[1].uppercase()),
                comparison = when (ruleMatch.groupValues[2][0]) {
                  '<' -> Comparison.LESS_THAN
                  else -> Comparison.GREATER_THAN
                },
                value = ruleMatch.groupValues[3].toInt(),
                outcome = when (val outcome = ruleMatch.groupValues[4]) {
                  "A" -> Rule.Result(true)
                  "R" -> Rule.Result(false)
                  else -> Rule.Jump(outcome)
                },
              )
            }
          }
        }
      }
    )
  }

  private fun parsePart(part: String): Part {
    return part.substring(1, part.length - 1).splitCommas().associate {
      val match = RATING_REGEX.matchEntire(it)!!
      Category.valueOf(match.groupValues[1].uppercase()) to match.groupValues[2].toInt()
    }
  }
}
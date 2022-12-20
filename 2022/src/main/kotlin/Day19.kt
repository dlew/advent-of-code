object Day19 {

  fun part1(input: String): Int {
    return parseInput(input)
      .sumOf { blueprint -> maxGeodes(blueprint, 24) * blueprint.number }
  }

  fun part2(input: String): Int {
    return parseInput(input)
      .take(3)
      .map { blueprint -> maxGeodes(blueprint, 32) }
      .reduce(Int::times)
  }

  private fun maxGeodes(blueprint: Blueprint, timeLimit: Int): Int {
    val queue = mutableListOf(State())
    val explored = mutableSetOf(State())

    var bestNumberGeodes = 0
    while (queue.isNotEmpty()) {
      val state = queue.removeLast()

      if (state.calculateMostPossibleGeodes(timeLimit) < bestNumberGeodes) {
        continue
      }

      // Otherwise, iterate through all possible next states
      val nextPossibleStates = buildRobots(state, blueprint, timeLimit)
        .map {
          it.copy(
            minute = state.minute + 1,
            ore = it.ore + state.oreBots,
            clay = it.clay + state.clayBots,
            obsidian = it.obsidian + state.obsidianBots,
            geodes = it.geodes + state.geodeBots
          )
        }
        .filter { it !in explored }

      explored.addAll(nextPossibleStates)

      // If we still have more time, add the next states to the queue
      if (state.minute != timeLimit) {
        queue.addAll(nextPossibleStates)
      } else if (nextPossibleStates.isNotEmpty()) {
        bestNumberGeodes = maxOf(bestNumberGeodes, nextPossibleStates.maxOf { it.geodes })
      }
    }

    return bestNumberGeodes
  }

  private fun buildRobots(state: State, blueprint: Blueprint, timeLimit: Int): List<State> {
    // If we're on the last minute, then there's no point in building anything; all we can do is collect
    if (state.minute == timeLimit) {
      return listOf(state)
    }

    // If you can build a geode bot, always do that!
    if (state.canBuildGeodeBot(blueprint)) {
      return listOf(state.buildGeodeBot(blueprint))
    }

    // If we're one minute before the end, and we couldn't build a geode bot, don't build anything else;
    // the next turn will just collect resources, there's no point to building
    if (state.minute == timeLimit - 1) {
      return listOf(state)
    }

    // List out possible bots we can build (though skip if they are unnecessary to victory)
    val nextPossibleStates = mutableListOf(state)
    if (state.canBuildObsidianBot(blueprint) && state.shouldBuildObsidianBot(blueprint)) {
      nextPossibleStates.add(state.buildObsidianBot(blueprint))
    }
    if (state.canBuildClayBot(blueprint) && state.shouldBuildClayBot(blueprint)) {
      nextPossibleStates.add(state.buildClayBot(blueprint))
    }
    if (state.canBuildOreBot(blueprint) && state.shouldBuildOreBot(blueprint)) {
      nextPossibleStates.add(state.buildOreBot(blueprint))
    }
    return nextPossibleStates
  }

  private val INPUT_REGEX =
    Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.")

  private fun parseInput(input: String): List<Blueprint> {
    return input.splitNewlines()
      .map { INPUT_REGEX.matchEntire(it)!!.destructured }
      .map { (a, b, c, d, e, f, g) ->
        Blueprint(
          number = a.toInt(),
          oreRobotOreCost = b.toInt(),
          clayRobotOreCost = c.toInt(),
          obsidianRobotOreCost = d.toInt(),
          obsidianRobotClayCost = e.toInt(),
          geodeRobotOreCost = f.toInt(),
          geodeRobotObsidianCost = g.toInt()
        )
      }
  }

  private data class Blueprint(
    val number: Int,
    val oreRobotOreCost: Int,
    val clayRobotOreCost: Int,
    val obsidianRobotOreCost: Int,
    val obsidianRobotClayCost: Int,
    val geodeRobotOreCost: Int,
    val geodeRobotObsidianCost: Int,
  ) {
    val maxOreNeededToBuildBot = maxOf(oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, geodeRobotOreCost)
  }

  private data class State(
    val minute: Int = 1,
    val ore: Int = 0,
    val oreBots: Int = 1,
    val clay: Int = 0,
    val clayBots: Int = 0,
    val obsidian: Int = 0,
    val obsidianBots: Int = 0,
    val geodes: Int = 0,
    val geodeBots: Int = 0,
  ) {

    fun canBuildOreBot(blueprint: Blueprint) = ore >= blueprint.oreRobotOreCost

    fun shouldBuildOreBot(blueprint: Blueprint) = oreBots < blueprint.maxOreNeededToBuildBot

    fun buildOreBot(blueprint: Blueprint) = this.copy(
      ore = ore - blueprint.oreRobotOreCost,
      oreBots = oreBots + 1,
    )

    fun canBuildClayBot(blueprint: Blueprint) = ore >= blueprint.clayRobotOreCost

    fun shouldBuildClayBot(blueprint: Blueprint): Boolean {
      return clayBots < blueprint.obsidianRobotClayCost
    }

    fun buildClayBot(blueprint: Blueprint) = this.copy(
      ore = ore - blueprint.clayRobotOreCost,
      clayBots = clayBots + 1
    )

    fun canBuildObsidianBot(blueprint: Blueprint) =
      ore >= blueprint.obsidianRobotOreCost && clay >= blueprint.obsidianRobotClayCost

    fun shouldBuildObsidianBot(blueprint: Blueprint): Boolean {
      return obsidianBots < blueprint.geodeRobotObsidianCost
    }

    fun buildObsidianBot(blueprint: Blueprint) = this.copy(
      ore = ore - blueprint.obsidianRobotOreCost,
      clay = clay - blueprint.obsidianRobotClayCost,
      obsidianBots = obsidianBots + 1
    )

    fun canBuildGeodeBot(blueprint: Blueprint) =
      ore >= blueprint.geodeRobotOreCost && obsidian >= blueprint.geodeRobotObsidianCost

    fun buildGeodeBot(blueprint: Blueprint) = this.copy(
      ore = ore - blueprint.geodeRobotOreCost,
      obsidian = obsidian - blueprint.geodeRobotObsidianCost,
      geodeBots = geodeBots + 1
    )

    fun calculateMostPossibleGeodes(timeLimit: Int): Int {
      val timeLeft = timeLimit - minute + 1
      val geodesFromExistingBots = geodeBots * timeLeft
      val geodesIfConstantlyBuildingBots = (timeLeft * (timeLeft + 1)) / 2
      return geodes + geodesFromExistingBots + geodesIfConstantlyBuildingBots
    }
  }

}
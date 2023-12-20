object Day20 {

  fun part1(input: String): Long {
    var modules = parse(input)

    var low = 0L
    var high = 0L
    repeat(1000) {
      val (nextModules, pulseCount) = pushButton(modules)
      low += pulseCount.low
      high += pulseCount.high
      modules = nextModules
    }

    return low * high
  }

  fun part2(input: String): Long {
    var modules = parse(input)

    // Find the module leading to rx
    val moduleLeadingToRx = modules.find { "rx" in it.destinations }!!

    // Find all conjunctions leading to the key
    val conjunctionsLeadingToRx = modules.filter { moduleLeadingToRx.name in it.destinations }.map { it.name }

    // It just so happens that the conjunctions leading to rx cycle, so detect their cycles
    var buttonCount = 0
    val cycles = mutableMapOf<String, Int>()
    while (true) {
      val (nextModules, _, conjunctionsThatPulsedHigh) = pushButton(modules)
      modules = nextModules
      buttonCount++

      // Add to cycles if we found another
      conjunctionsThatPulsedHigh
        .filter { name -> name in conjunctionsLeadingToRx }
        .forEach { name -> cycles[name] = buttonCount }

      // If we have all cycles, finish
      if (cycles.size == conjunctionsLeadingToRx.size) {
        return leastCommonMultiple(cycles.values.map { it.toLong() })
      }
    }
  }

  private fun pushButton(modules: List<Module>): Result {
    var low = 0L
    var high = 0L
    val queue = mutableListOf(Pulse("button", "broadcaster", false))
    val moduleMap = modules.associateBy { it.name }.toMutableMap()
    val conjunctionsThatPulsedHigh = mutableSetOf<String>()

    while (queue.isNotEmpty()) {
      val pulse = queue.removeFirst()

      if (pulse.isHigh) high++ else low++

      when (val module = moduleMap[pulse.destination]) {
        is Module.Broadcaster -> queue.addAll(module.destinations.map { Pulse(module.name, it, pulse.isHigh) })
        is Module.Conjunction -> {
          val newModuleState = module.copy(memory = module.memory + mapOf(pulse.source to pulse.isHigh))
          moduleMap[module.name] = newModuleState
          val sendHigh = !newModuleState.memory.values.all { it }
          if (sendHigh) {
            conjunctionsThatPulsedHigh.add(module.name)
          }
          queue.addAll(module.destinations.map { Pulse(module.name, it, sendHigh) })
        }

        is Module.FlipFlop -> {
          if (!pulse.isHigh) {
            moduleMap[module.name] = module.copy(on = !module.on)
            queue.addAll(module.destinations.map { Pulse(module.name, it, !module.on) })
          }
        }

        null -> {
          // Theoretically, this is the pulse to rx, but the number is so high as to be unrealistic to ever hit
        }
      }
    }

    return Result(moduleMap.values.toList(), PulseCount(low, high), conjunctionsThatPulsedHigh)
  }

  private fun leastCommonMultiple(numbers: List<Long>) = numbers.reduce { a, b -> leastCommonMultiple(a, b) }

  private fun leastCommonMultiple(a: Long, b: Long) = a * (b / greatestCommonDivisor(a, b))

  // Euclid's algorithm
  private fun greatestCommonDivisor(a: Long, b: Long): Long = if (b == 0L) a else greatestCommonDivisor(b, a % b)


  private sealed class Module {
    abstract val name: String
    abstract val destinations: List<String>

    data class Broadcaster(override val destinations: List<String>) : Module() {
      override val name = "broadcaster"
    }

    data class FlipFlop(override val name: String, override val destinations: List<String>, val on: Boolean = false) :
      Module()

    data class Conjunction(
      override val name: String,
      override val destinations: List<String>,
      val memory: Map<String, Boolean> = emptyMap()
    ) : Module()
  }

  private data class Pulse(val source: String, val destination: String, val isHigh: Boolean)

  private data class PulseCount(val low: Long, val high: Long)

  private data class Result(
    val modules: List<Module>,
    val pulseCount: PulseCount,
    val conjunctionsThatPulsedHigh: Set<String>
  )

  private fun parse(input: String): List<Module> {
    val inputs = mutableMapOf<String, MutableList<String>>()
    val modules = input.splitNewlines().map {
      val (nameAndFunction, destinationsStr) = it.split(" -> ")
      val destinations = destinationsStr.splitCommas()

      val module = when {
        nameAndFunction == "broadcaster" -> Module.Broadcaster(destinations)
        nameAndFunction.startsWith("%") -> Module.FlipFlop(nameAndFunction.drop(1), destinations)
        else -> Module.Conjunction(nameAndFunction.drop(1), destinations)
      }

      module.destinations.forEach { destination ->
        val inputList = inputs.getOrPut(destination) { mutableListOf() }
        inputList.add(module.name)
      }

      return@map module
    }.toMutableList()

    // Set the memory for every conjunction module
    modules.indices.forEach { index ->
      val module = modules[index]
      if (module is Module.Conjunction) {
        modules[index] = module.copy(memory = inputs[module.name].orEmpty().associateWith { false })
      }
    }

    return modules
  }

}
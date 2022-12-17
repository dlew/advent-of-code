import Day16.Worker.Active

object Day16 {

  fun part1(input: String) = bestRoute(input, 30, 1)

  fun part2(input: String) = bestRoute(input, 26, 2)

  private fun bestRoute(input: String, time: Int, numWorkers: Int): Int {
    val valves = parseInput(input)
    val distanceMap = generateDistanceMap(valves)
    val usefulValves = valves.filter { it.flowRate != 0 }
    val startValve = valves.find { it.label == "AA" }!!
    return bestRoute(
      pressureReleased = 0,
      timeLeft = time,
      workerState = List(numWorkers) { Worker.Idle(startValve) },
      usefulValves = usefulValves,
      distanceMap = distanceMap
    )
  }

  // I know this is a bad solution, because it takes a while to run - but it does come across the correct answer
  // at some point. Maybe if I have free time (ha ha) I will fix it up later!
  private fun bestRoute(
    pressureReleased: Int,
    timeLeft: Int,
    workerState: List<Worker>,
    usefulValves: List<Valve>,
    distanceMap: Map<Path, Int>,
  ): Int {
    // Finish any jobs
    val finishedWorkers = workerState
      .filterIsInstance<Active>()
      .filter { it.timeFinished == timeLeft }
      .toSet()
    val newPressureReleased = pressureReleased + finishedWorkers.sumOf { it.destination.flowRate * timeLeft }

    // Finished workers become idle again
    val newWorkerState = workerState - finishedWorkers + finishedWorkers.map { Worker.Idle(it.destination) }

    // Nothing more we can do, exit
    if (usefulValves.isEmpty()) {
      if (newWorkerState.any { it is Active }) {
        val nextTime = newWorkerState.filterIsInstance<Active>().maxOf { it.timeFinished }
        return bestRoute(
          newPressureReleased,
          nextTime,
          newWorkerState,
          usefulValves,
          distanceMap
        )
      }

      return newPressureReleased
    }

    // If we reach here, we should always have at least one idle worker, assign them work
    return newWorkerState.filterIsInstance<Worker.Idle>().distinct().maxOf { idleWorker ->
      usefulValves.maxOf { valve ->
        val curr = idleWorker.curr
        val nextWorkerState =
          newWorkerState - idleWorker + Active(valve, timeLeft - distanceMap[Path(curr, valve)]!! - 1)
        val nextTime =
          if (nextWorkerState.any { it is Worker.Idle }) timeLeft else nextWorkerState.filterIsInstance<Active>()
            .maxOf { it.timeFinished }

        // We would run out of time, not a valid solution
        if (nextTime < 0) {
          return@maxOf newPressureReleased
        }

        bestRoute(
          newPressureReleased,
          nextTime,
          nextWorkerState,
          usefulValves - valve,
          distanceMap
        )
      }
    }
  }

  private fun generateDistanceMap(valves: List<Valve>): Map<Path, Int> {
    val valveMap = valves.associateBy { it.label }
    val distanceMap = mutableMapOf<Path, Int>()

    valves.indices.forEach { a ->
      val from = valves[a]
      valves.indices.drop(a + 1).forEach { b ->
        val to = valves[b]
        val path = Path(from, to)
        if (path !in distanceMap) {
          val distance = distance(valveMap, from, to)
          distanceMap[path] = distance
          distanceMap[Path(to, from)] = distance
        }
      }
    }

    return distanceMap
  }

  private fun distance(valves: Map<String, Valve>, from: Valve, to: Valve): Int {
    var next = listOf(from)
    val explored = mutableSetOf(from)

    var distance = 0
    while (next.isNotEmpty()) {
      val tunnels = next.flatMap { it.tunnels }.distinct()

      if (to.label in tunnels) {
        return distance + 1
      }

      explored.addAll(next)
      next = tunnels
        .map { valves[it]!! }
        .filter { it !in explored }
      distance++
    }

    throw IllegalStateException("No path found from $from to $to")
  }

  private val INPUT_REGEX = Regex("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)")

  private fun parseInput(input: String): List<Valve> {
    return input.splitNewlines()
      .map { INPUT_REGEX.matchEntire(it)!!.destructured }
      .map { (label, flowRate, tunnels) -> Valve(label, flowRate.toInt(), tunnels.splitCommas()) }
  }

  private sealed class Worker {
    data class Idle(val curr: Valve) : Worker()
    data class Active(val destination: Valve, var timeFinished: Int) : Worker()
  }

  data class Valve(val label: String, val flowRate: Int, val tunnels: List<String>) {
    override fun toString() = "$label($flowRate)"
  }

  data class Path(val from: Valve, val to: Valve)

}
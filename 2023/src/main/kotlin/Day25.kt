object Day25 {

  fun part1(input: String): Int {
    val graph = parse(input)

    // Find the components with the shortest paths to every other node - those *must* be the nodes between the two groups
    val distances = graph.keys
      .map { component -> Distance(component, furthestComponent(component, graph)) }
      .sortedBy { it.distance }

    val shortestDistanceComponents = distances
      .filter { it.distance == distances[0].distance }
      .map { it.component }

    // Try removing each edge from the nodes; the result that has the highest distance (because it has to cross the gap
    // at a different point) is the one that needs to be cut, then cut it from the graph
    val dividedGraph = graph.toMutableMap()
    shortestDistanceComponents.forEach { component ->
      // Try removing each edge from the start from the graph. The result w/ the highest distance
      // must be the one connecting the groups (since cutting it means you have to "the long way" now).
      val componentToCut = graph[component]!!
        .map { ignoredComponent -> Distance(ignoredComponent, furthestComponent(component, graph, ignoredComponent)) }
        .maxBy { it.distance }
        .component

      // Cut the longest path from the graph
      dividedGraph[component] = dividedGraph[component]!! - componentToCut
      dividedGraph[componentToCut] = dividedGraph[componentToCut]!! - component
    }

    // Find the size of one group and you have the size of the other
    val group1 = numberOfReachableComponents(graph.keys.first(), dividedGraph)
    val group2 = graph.keys.size - group1
    return group1 * group2
  }

  private fun furthestComponent(start: String, graph: Map<String, List<String>>, ignoreEdge: String? = null): Int {
    val queue = java.util.ArrayDeque<Distance>()
    val visited = mutableMapOf(start to 0)
    queue.addAll(graph[start]!!
      .filter { it != ignoreEdge }
      .map { Distance(it, 1) }
    )

    while (queue.isNotEmpty()) {
      val (component, distance) = queue.pop()

      if (visited.containsKey(component)) {
        continue
      }

      visited[component] = distance

      queue.addAll(graph[component]!!.map { Distance(it, distance + 1) })
    }

    return visited.maxOf { it.value }
  }

  private fun numberOfReachableComponents(start: String, graph: Map<String, List<String>>): Int {
    val queue = java.util.ArrayDeque<String>()
    val visited = mutableSetOf(start)
    queue.addAll(graph[start]!!)
    while (queue.isNotEmpty()) {
      val component = queue.pop()
      if (component in visited) {
        continue
      }
      visited.add(component)
      queue.addAll(graph[component]!!)
    }
    return visited.size
  }

  private data class Distance(val component: String, val distance: Int)

  private fun parse(input: String): Map<String, List<String>> {
    val map = mutableMapOf<String, MutableList<String>>()

    fun connect(a: String, b: String) {
      map.getOrPut(a) { mutableListOf() }.add(b)
      map.getOrPut(b) { mutableListOf() }.add(a)
    }

    input.splitNewlines().forEach { line ->
      val components = line.splitWhitespace()
      val a = components[0].dropLast(1)
      components.drop(1).forEach { b -> connect(a, b) }
    }

    return map
  }
}
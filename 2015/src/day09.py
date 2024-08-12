import re


def day9_part1(data: str) -> int:
    graph = _parse_graph(data)
    return min([_traversal(graph, start, set(), 0, True) for start in list(graph.keys())])


def day9_part2(data: str) -> int:
    graph = _parse_graph(data)
    return max([_traversal(graph, start, set(), 0, False) for start in list(graph.keys())])


def _traversal(graph: dict[str, dict[str, int]], curr: str, used: set[str], cost: int, minimize: bool) -> int:
    if len(graph) == len(used) + 1:
        return cost

    used_with_curr = used.copy()
    used_with_curr.add(curr)

    options = [
        _traversal(graph, dest, used_with_curr, cost + graph[curr][dest], minimize)
        for dest in graph[curr].keys()
        if dest not in used
    ]

    return min(options) if minimize else max(options)


def _parse_graph(data: str) -> dict[str, dict[str, int]]:
    pattern = re.compile(r"(\w+) to (\w+) = (\d+)")
    graph = {}
    for line in data.splitlines():
        match = pattern.match(line)
        start, end, distance = match.groups()

        if start not in graph:
            graph[start] = {}
        graph[start][end] = int(distance)

        if end not in graph:
            graph[end] = {}
        graph[end][start] = int(distance)

    return graph

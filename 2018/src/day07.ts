export function orderSteps(edges: Array<Edge>): string {
  const nodes = getNodes(edges);

  let order = "";
  let node = nextNode(nodes, edges, order);
  while (node != null) {
    order += node;
    edges = edges.filter((edge) => edge.from !== node);
    node = nextNode(nodes, edges, order);
  }

  return order;
}

export function orderStepsWithWorkers(
  edges: Array<Edge>,
  numWorkers: number,
  stepTime: number
): number {
  const nodes = getNodes(edges);
  const workers = new Map<string, number>();

  let assigned = "";
  let order = "";

  // Keep the loop going until each task is done
  let timeSpent = 0;
  while (order.length < nodes.size) {
    // Check if any work was done in the last second
    for (const [node, timeLeft] of workers) {
      if (timeLeft == 1) {
        workers.delete(node);
        order += node;
        edges = edges.filter((edge) => edge.from !== node);
      } else {
        workers.set(node, timeLeft - 1);
      }
    }

    // Assign any available work
    const nextNodes = getAllAvailableWork(nodes, edges, order);
    while (workers.size < numWorkers) {
      const node = nextNodes.shift();
      if (node === undefined) {
        break;
      } else if (assigned.includes(node)) {
        continue;
      }

      workers.set(node, taskTime(stepTime, node));
      assigned += node;
    }

    timeSpent++;
  }

  return timeSpent - 1;
}

function getNodes(edges: Array<Edge>): Set<string> {
  const nodes = new Set<string>();
  edges.forEach((edge) => {
    nodes.add(edge.from);
    nodes.add(edge.to);
  });
  return nodes;
}

// Finds the next node to work on; not particularly efficient but it works
function nextNode(
  nodes: Set<string>,
  edges: Array<Edge>,
  visited: string
): string | null {
  return getAllAvailableWork(nodes, edges, visited)[0];
}

function getAllAvailableWork(
  nodes: Set<string>,
  edges: Array<Edge>,
  visited: string
): Array<string> {
  if (edges.length != 0) {
    const toNodes = new Set<string>();
    edges.forEach((edge) => toNodes.add(edge.to));
    return Array.from(nodes)
      .filter((node) => !toNodes.has(node) && !visited.includes(node))
      .sort();
  } else {
    return [...nodes].filter((node) => !visited.includes(node)).sort();
  }
}

function taskTime(stepTime: number, node: string) {
  return stepTime + node.charCodeAt(0) - 64;
}

const line_regexp = /Step (.+) must be finished before step (.+) can begin./;

export function parseGraph(text: string): Array<Edge> {
  const edges = new Array<Edge>();

  text.split(/\r?\n/).forEach((line) => {
    const match = line.match(line_regexp)!;
    const from = match[1];
    const to = match[2];

    edges.push(new Edge(from, to));
  });

  return edges;
}

class Edge {
  readonly from: string;
  readonly to: string;

  constructor(from: string, to: string) {
    this.from = from;
    this.to = to;
  }
}

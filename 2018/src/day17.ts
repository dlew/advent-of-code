import { filter, last, maxBy, minBy, sumBy } from "lodash";

interface Point {
  x: number;
  y: number;
}

interface Result {
  settled: number;
  flowing: number;
}

enum State {
  CLAY = "#",
  SETTLED = "~",
  FLOWING = "|",
  BEDROCK = "^",
  NONE = ".",
}

enum FlowState {
  BLOCKED,
  UNBLOCKED,
  UNKNOWN,
}

export function part1(input: string): number {
  const clay = parse(input);
  const result = trickle(clay);
  return result.settled + result.flowing;
}

export function part2(input: string): number {
  const clay = parse(input);
  const result = trickle(clay);
  return result.settled;
}

function trickle(clay: Point[]): Result {
  const maxX = maxBy(clay, "x")!.x;
  const minY = minBy(clay, "y")!.y;
  const maxY = maxBy(clay, "y")!.y;

  // Setup the grid w/ clay and bedrock
  const grid: State[][] = Array(0);
  for (let y = 0; y < maxY + 2; y++) {
    grid.push(Array(maxX + 2).fill(State.NONE));
  }

  clay.forEach(({ x, y }) => {
    grid[y][x] = State.CLAY;
  });

  // We fill the bottom with "bedrock" to avoid overflowing the bottom
  last(grid)!.fill(State.BEDROCK);

  // We start at 500, minY to avoid overcounting the top
  grid[minY][500] = State.FLOWING;

  const queue: Point[] = [{ x: 500, y: minY }];
  while (queue.length !== 0) {
    const curr = queue.shift()!;

    // Someone other branch filled this in - don't bother working here
    if (grid[curr.y][curr.x] == State.SETTLED) {
      continue;
    }

    const below = grid[curr.y + 1][curr.x];
    switch (below) {
      // We have more space to fall; queue it up
      case State.NONE:
      case State.FLOWING: {
        grid[curr.y + 1][curr.x] = State.FLOWING;
        queue.push({ x: curr.x, y: curr.y + 1 });
        break;
      }

      // We've flowed to the bottom; stop bothering with this flow
      case State.BEDROCK:
        break;

      // We've hit a barrier; flood the area
      case State.CLAY:
      case State.SETTLED: {
        // Span out left and right to see if we hit walls on either end
        let leftState = FlowState.UNKNOWN;
        let leftX = curr.x - 1;
        while (leftState == FlowState.UNKNOWN) {
          // We've hit a wall
          if (isWall(grid[curr.y][leftX])) {
            leftState = FlowState.BLOCKED;
          }
          // We've hit a ledge
          else if (canFlow(grid[curr.y + 1][leftX])) {
            leftState = FlowState.UNBLOCKED;
          }
          // Keep going
          else {
            leftX--;
          }
        }

        let rightState = FlowState.UNKNOWN;
        let rightX = curr.x + 1;
        while (rightState == FlowState.UNKNOWN) {
          // We've hit a wall
          if (isWall(grid[curr.y][rightX])) {
            rightState = FlowState.BLOCKED;
          }
          // We've hit a ledge
          else if (canFlow(grid[curr.y + 1][rightX])) {
            rightState = FlowState.UNBLOCKED;
          }
          // Keep going
          else {
            rightX++;
          }
        }

        // We're blocked on both sides; fill all squares with settled water, then queue up the flow above this spot
        if (rightState == FlowState.BLOCKED && leftState == FlowState.BLOCKED) {
          for (let x = leftX + 1; x < rightX; x++) {
            grid[curr.y][x] = State.SETTLED;
          }
          queue.push({ x: curr.x, y: curr.y - 1 });
        } else {
          // We're not blocked on some side; mark all squares as flowing, then queue up the unblocked sides to flow downwards
          for (let x = leftX + 1; x < rightX; x++) {
            grid[curr.y][x] = State.FLOWING;
          }

          if (leftState == FlowState.UNBLOCKED) {
            grid[curr.y][leftX] = State.FLOWING;
            queue.push({ x: leftX, y: curr.y });
          }

          if (rightState == FlowState.UNBLOCKED) {
            grid[curr.y][rightX] = State.FLOWING;
            queue.push({ x: rightX, y: curr.y });
          }
        }
        break;
      }
    }
  }

  return {
    settled: countStates(grid, State.SETTLED),
    flowing: countStates(grid, State.FLOWING),
  };
}

function isWall(state: State): boolean {
  return state === State.CLAY || state === State.SETTLED;
}

function canFlow(state: State): boolean {
  return state === State.NONE || state === State.FLOWING;
}

function countStates(grid: State[][], countState: State): number {
  return sumBy(grid, (row) => {
    return filter(row, (state) => state === countState).length;
  });
}

function parse(input: string): Point[] {
  return input.split(/\r?\n/).flatMap((line) => {
    const clay: Point[] = [];

    if (line.startsWith("x")) {
      const match = line.match(/x=(\d+), y=(\d+)\.\.(\d+)/)!;
      const x = Number(match[1]);
      const yStart = Number(match[2]);
      const yEnd = Number(match[3]);
      for (let y = yStart; y <= yEnd; y++) {
        clay.push({ x, y });
      }
    } else {
      const match = line.match(/y=(\d+), x=(\d+)\.\.(\d+)/)!;
      const y = Number(match[1]);
      const xStart = Number(match[2]);
      const xEnd = Number(match[3]);
      for (let x = xStart; x <= xEnd; x++) {
        clay.push({ x, y });
      }
    }

    return clay;
  });
}

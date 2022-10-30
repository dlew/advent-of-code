const VERBOSE = false;

export function simulateCombat(input: string): number {
  const board = parseInput(input);
  return runSimulation(board);
}

export function keepElvesAlive(input: string): number {
  let board = parseInput(input);
  const numElves = board.countElves();
  let elfAttackPower = 3;
  do {
    board = parseInput(input, elfAttackPower);
    runSimulation(board);
    elfAttackPower++;
  } while (board.countElves() !== numElves);

  return board.outcome();
}

function runSimulation(board: Board): number {
  let hasEnemies = true;
  do {
    if (VERBOSE) {
      printBoard(board);
    }
    hasEnemies = board.tick();
  } while (hasEnemies);

  if (VERBOSE) {
    console.log("\nEnd game:");
    printBoard(board);
  }

  return board.outcome();
}

class Board {
  round = 0;

  constructor(readonly grid: Space[][], readonly elfAttackPower = 3) {}

  outcome(): number {
    return this.round * this.totalHitpoints();
  }

  private totalHitpoints(): number {
    let totalHp = 0;
    this.grid.forEach((row, y) => {
      row.forEach((space, x) => {
        if (this.isUnit(space)) {
          totalHp += space.hp;
        }
      });
    });
    return totalHp;
  }

  countElves(): number {
    let elves = 0;
    this.grid.forEach((row, y) => {
      row.forEach((space, x) => {
        if (this.isUnit(space) && space.type === UnitType.ELF) {
          elves++;
        }
      });
    });
    return elves;
  }

  tick(): boolean {
    const order = this.getTurnOrder();
    for (let a = 0; a < order.length; a++) {
      const { x, y } = order[a];
      const unit = this.grid[y][x];

      // Unit was destroyed; skip their turn
      if (!this.isUnit(unit)) {
        continue;
      }

      const foundEnemies = this.takeTurn(order[a], unit);
      if (!foundEnemies) {
        return false;
      }
    }

    this.round++;
    return true;
  }

  // return "false" if a unit found no enemies
  private takeTurn(position: Position, unit: Unit): boolean {
    if (VERBOSE) {
      console.log(
        `${unit.type} taking turn starting at ${position.x},${position.y}`
      );
    }

    let currPos = position;

    // Find all remaining targets
    const targets = this.findTargets(unit);

    // No remaining targets - skip turn
    if (targets.length === 0) {
      return false;
    }

    // If we are not adjacent to a target, try to move
    if (
      !targets.some((target) => this.manhattanDistance(currPos, target) === 1)
    ) {
      // Find all shortest paths to a target
      let nextMoves: Array<Position> = [];
      let closestDistance = Number.MAX_VALUE;
      targets.forEach((target) => {
        const path = this.shortestPathToPosition(currPos, target);
        if (path !== null) {
          if (path.length === closestDistance) {
            nextMoves.push(path[1]);
          } else if (path.length < closestDistance) {
            nextMoves = [path[1]];
            closestDistance = path.length;
          }
        }
      });

      // Pick the shortest path to a target in reading order
      nextMoves.sort((p1, p2) => {
        if (p1.y !== p2.y) {
          return p1.y - p2.y;
        }
        return p1.x - p2.x;
      });

      // Move towards the closest target (if not already adjacent)
      if (nextMoves.length !== 0) {
        currPos = nextMoves[0];
        this.grid[position.y][position.x] = ".";
        this.grid[currPos.y][currPos.x] = unit;
        if (VERBOSE) {
          console.log(`${unit.type} moving to ${currPos.x},${currPos.y}`);
        }
      }
    }

    // Attack (if adjacent to enemy)
    const adjacentTargets = targets.filter((target) => {
      return this.manhattanDistance(currPos, target) === 1;
    });

    if (adjacentTargets.length === 0) {
      return true;
    }

    // Find adjacent target with the least health
    let target = adjacentTargets[0];
    let targetHp = (this.grid[target.y][target.x] as Unit).hp;
    for (let a = 1; a < adjacentTargets.length; a++) {
      const otherTarget = adjacentTargets[a];
      const otherTargetHp = (this.grid[otherTarget.y][otherTarget.x] as Unit)
        .hp;
      if (otherTargetHp < targetHp) {
        target = otherTarget;
        targetHp = otherTargetHp;
      }
    }

    // Attack!
    if (VERBOSE) {
      console.log(`${unit.type} attacking ${target.x},${target.y}`);
    }

    const enemy = this.grid[target.y][target.x] as Unit;
    const attackPower = unit.type === UnitType.GOBLIN ? 3 : this.elfAttackPower;
    enemy.hp -= attackPower;

    // Enemy destroyed!
    if (enemy.hp <= 0) {
      this.grid[target.y][target.x] = ".";
    }

    return true;
  }

  private manhattanDistance(p1: Position, p2: Position): number {
    return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
  }

  // Returns array of steps (starting at `start`) which  leads you to `end`.
  private shortestPathToPosition(
    start: Position,
    end: Position
  ): Position[] | null {
    // BFS since the cost of each move is the same
    const queue: Array<Array<Position>> = [[start]];
    const visited = new Set<string>();

    while (queue.length !== 0) {
      const path = queue.shift()!;
      const pos = path[path.length - 1];

      // Check if already visited
      const setKey = `${pos.x},${pos.y}`;
      if (visited.has(setKey)) {
        continue;
      }

      visited.add(setKey);

      // Check if we got to the destination
      if (pos.x === end.x && pos.y === end.y) {
        return path;
      }

      // Check if we ran into something else
      const space = this.grid[pos.y][pos.x];
      if (pos !== start && space !== ".") {
        continue;
      }

      // Append each cardinal direction to the queue (in reading order)
      queue.push(
        path.concat({ x: pos.x, y: pos.y - 1 }),
        path.concat({ x: pos.x - 1, y: pos.y }),
        path.concat({ x: pos.x + 1, y: pos.y }),
        path.concat({ x: pos.x, y: pos.y + 1 })
      );
    }

    // There is no path to the destination
    return null;
  }

  private findTargets(unit: Unit): Position[] {
    const enemy = unit.type === UnitType.ELF ? UnitType.GOBLIN : UnitType.ELF;
    const targets: Position[] = [];
    this.grid.forEach((row, y) => {
      row.forEach((space, x) => {
        if (this.isUnit(space) && space.type === enemy) {
          targets.push({ x, y });
        }
      });
    });
    return targets;
  }

  private getTurnOrder(): Position[] {
    const order: Position[] = [];
    this.grid.forEach((row, y) => {
      row.forEach((space, x) => {
        if (this.isUnit(space)) {
          order.push({ x, y });
        }
      });
    });
    return order;
  }

  private isUnit(space: Space): space is Unit {
    return space !== "#" && space !== ".";
  }

  toString() {
    let output = `Round ${this.round}:\n`;
    this.grid.forEach((row) => {
      const units: Unit[] = [];
      row.forEach((space) => {
        if (this.isUnit(space)) {
          units.push(space);
          output += space.type;
        } else {
          output += space;
        }
      });

      if (units.length !== 0) {
        output += "   ";
        output += units.map((unit) => `${unit.type}(${unit.hp})`).join(", ");
      }

      output += "\n";
    });
    output += `Total HP=${this.totalHitpoints()}\n`;
    return output;
  }
}

function printBoard(board: Board) {
  console.log(`${board}`);
}

function parseInput(input: string, elfAttackPower = 3): Board {
  return new Board(
    input.split(/\r?\n/).map((line) =>
      line.split("").map((char) => {
        switch (char) {
          case "#":
          case ".":
            return char;
          case "E":
            return {
              type: UnitType.ELF,
              hp: 200,
            };
          case "G":
            return {
              type: UnitType.GOBLIN,
              hp: 200,
            };
          default:
            throw new Error(`Unrecognized character: ${char}`);
        }
      })
    ),
    elfAttackPower
  );
}

type Space = "#" | "." | Unit;

enum UnitType {
  ELF = "E",
  GOBLIN = "G",
}

type Position = {
  x: number;
  y: number;
};

type Unit = {
  type: UnitType;
  hp: number;
};

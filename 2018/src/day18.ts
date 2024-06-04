import { countBy, find } from "lodash";

export function part1(input: string): number {
  let area = parse(input);
  for (let i = 0; i < 10; i++) {
    area = iterate(area);
  }
  return score(area);
}

export function part2(input: string): number {
  let area = parse(input);

  const repetitions = 1000000000;
  const memo = new Map<string, number>();
  for (let iteration = 0; iteration < repetitions; iteration++) {
    const areaKey = area.join("");
    const startOfCycle = memo.get(areaKey);
    if (startOfCycle !== undefined) {
      const cycleSize = memo.size - startOfCycle;
      const cycleSpot = (repetitions - startOfCycle) % cycleSize;
      const position = startOfCycle + cycleSpot;
      const scoredArea = find(
        [...memo.entries()],
        (entry) => entry[1] == position,
      )![0];
      return score(parse(scoredArea));
    }
    memo.set(areaKey, iteration);
    area = iterate(area);
  }

  throw new Error("Did not find cycle!");
}

function iterate(area: string[]): string[] {
  const newArea: string[][] = Array(area.length)
    .fill([])
    .map(() => Array(area[0].length));

  for (let y = 0; y < area.length; y++) {
    for (let x = 0; x < area[0].length; x++) {
      const curr = area[y][x];
      const counts = countAdjacent(area, x, y);
      switch (curr) {
        case ".":
          newArea[y][x] = counts.trees >= 3 ? "|" : ".";
          break;
        case "|":
          newArea[y][x] = counts.lumberyards >= 3 ? "#" : "|";
          break;
        case "#":
          newArea[y][x] =
            counts.trees > 0 && counts.lumberyards > 0 ? "#" : ".";
          break;
        default:
          newArea[y][x] = curr;
      }
    }
  }

  return newArea.map(it => it.join(""));
}

function countAdjacent(
  area: string[],
  centerX: number,
  centerY: number,
): Counts {
  let trees = 0;
  let lumberyards = 0;

  for (let y = centerY - 1; y <= centerY + 1; y++) {
    if (y < 0 || y >= area.length) {
      continue
    }

    for (let x = centerX - 1; x <= centerX + 1; x++) {
      if (x < 0 || x >= area[0].length || (x == centerX && y == centerY)) {
        continue
      }

      const acre = area[y][x]
      if (acre === '|') {
        trees++
      }
      else if (acre === '#') {
        lumberyards++
      }
    }
  }

  return { trees, lumberyards };
}

interface Counts {
  readonly trees: number;
  readonly lumberyards: number;
}

function score(area: string[]): number {
  const allChars = area.flatMap((row) => row.split(""));
  const counts = countBy(allChars, (char) => char);
  return counts["|"] * counts["#"];
}

function parse(input: string): string[] {
  return input.split(/\r?\n/);
}

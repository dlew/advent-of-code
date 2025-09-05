import re

from utils import raise_none

_PATTERN = re.compile(r"(.*) (\d+),(\d+) through (\d+),(\d+)")


def day6_part1(data: str) -> int:
    grid = [[False] * 1000 for _ in range(1000)]
    for line in data.splitlines():
        action, start_x, start_y, end_x, end_y = raise_none(_PATTERN.match(line)).groups()
        for x in range(int(start_x), int(end_x) + 1):
            for y in range(int(start_y), int(end_y) + 1):
                match action:
                    case "turn on":
                        grid[y][x] = True
                    case "toggle":
                        grid[y][x] = not grid[y][x]
                    case "turn off":
                        grid[y][x] = False

    count = 0
    for row in grid:
        count += sum([1 if light else 0 for light in row])
    return count


def day6_part2(data: str) -> int:
    grid = [[0] * 1000 for _ in range(1000)]
    for line in data.splitlines():
        action, start_x, start_y, end_x, end_y = raise_none(_PATTERN.match(line)).groups()
        for x in range(int(start_x), int(end_x) + 1):
            for y in range(int(start_y), int(end_y) + 1):
                match action:
                    case "turn on":
                        grid[y][x] += 1
                    case "toggle":
                        grid[y][x] += 2
                    case "turn off":
                        grid[y][x] = max(grid[y][x] - 1, 0)

    count = 0
    for row in grid:
        count += sum(row)
    return count

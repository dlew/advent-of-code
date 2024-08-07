import dataclasses
from dataclasses import dataclass


@dataclass(frozen=True)
class _Point:
    x: int
    y: int

    def move(self, direction: str):
        match direction:
            case "^":
                return dataclasses.replace(self, y=self.y - 1)
            case ">":
                return dataclasses.replace(self, x=self.x + 1)
            case "v":
                return dataclasses.replace(self, y=self.y + 1)
            case "<":
                return dataclasses.replace(self, x=self.x - 1)


def day3_part1(data: str) -> int:
    santa = _Point(0, 0)
    visited = {santa}
    for c in data:
        santa = santa.move(c)
        visited.add(santa)
    return len(visited)


def day3_part2(data: str) -> int:
    santa = _Point(0, 0)
    robo_santa = _Point(0, 0)
    visited = {santa}
    for index, c in enumerate(data):
        if index % 2 == 0:
            santa = santa.move(c)
            visited.add(santa)
        else:
            robo_santa = robo_santa.move(c)
            visited.add(robo_santa)
    return len(visited)

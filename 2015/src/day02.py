class _Box:
    def __init__(self, dimens: str):
        split = dimens.split("x")
        self.l = int(split[0])
        self.w = int(split[1])
        self.h = int(split[2])

    def paper_needed(self):
        surface_area = (
            (2 * self.l * self.w) + (2 * self.w * self.h) + (2 * self.h * self.l)
        )
        smallest_side = min(self.l * self.w, self.l * self.h, self.w * self.h)
        return surface_area + smallest_side

    def ribbon_needed(self):
        shortest_perimeter = min(
            2 * (self.l + self.w), 2 * (self.w + self.h), 2 * (self.h + self.l)
        )
        volume = self.l * self.w * self.h
        return shortest_perimeter + volume


def day2_part1(data: str) -> int:
    return sum([box.paper_needed() for box in _parse(data)])


def day2_part2(data: str) -> int:
    return sum([box.ribbon_needed() for box in _parse(data)])


def _parse(data: str) -> list[_Box]:
    return [_Box(line) for line in data.splitlines()]

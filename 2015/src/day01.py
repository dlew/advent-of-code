def day1_part1(data: str) -> int:
    return sum(1 if c == "(" else -1 for c in data)


def day1_part2(data: str) -> int:
    floor = 0
    for pos in range(len(data)):
        floor += 1 if data[pos] == "(" else -1
        if floor == -1:
            return pos + 1

    raise RuntimeError("Invalid input, never hits basement")

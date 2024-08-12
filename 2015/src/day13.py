import itertools
import re


def day13_part1(data: str) -> int:
    matrix = _parse(data)
    people = list(matrix.keys())
    return max([_calculate_happiness(matrix, list(arrangement)) for arrangement in itertools.permutations(people)])


def day13_part2(data: str) -> int:
    matrix = _parse(data)
    people = list(matrix.keys())

    # Add yourself to the mix
    matrix["You"] = {}
    for person in people:
        matrix["You"][person] = 0
        matrix[person]["You"] = 0
    people.append("You")

    return max([_calculate_happiness(matrix, list(arrangement)) for arrangement in itertools.permutations(people)])


def _calculate_happiness(matrix: dict[str, dict[str, int]], arrangement: list[str]) -> int:
    num_seats = len(arrangement)
    happiness = 0
    for seat in range(num_seats):
        left = (seat + num_seats - 1) % num_seats
        right = (seat + 1) % num_seats
        happiness += matrix[arrangement[seat]][arrangement[left]]
        happiness += matrix[arrangement[seat]][arrangement[right]]
    return happiness


def _parse(data: str) -> dict[str, dict[str, int]]:
    matrix = {}
    pattern = re.compile(r"(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+)\.")
    for line in data.splitlines():
        match = pattern.match(line)
        person, gain_or_loss, units, target = match.groups()
        value = int(units) if gain_or_loss == 'gain' else -int(units)
        if person not in matrix:
            matrix[person] = {}
        matrix[person][target] = value

    return matrix

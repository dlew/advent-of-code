import re


def day5_part1(data: str) -> int:
    return sum([1 if _is_nice(line) else 0 for line in data.splitlines()])


def day5_part2(data: str) -> int:
    return sum([1 if _is_nicer(line) else 0 for line in data.splitlines()])


def _is_nice(line: str) -> bool:
    three_vowels = re.compile(r"(.*[aeiou].*){3,}")
    double_letter = re.compile(r"(\w)\1+")
    forbidden = re.compile(r"ab|cd|pq|xy")
    return (re.match(three_vowels, line) is not None
            and re.search(double_letter, line) is not None
            and re.search(forbidden, line) is None)


def _is_nicer(line: str) -> bool:
    triples = _window(line, 3)
    if len([x for x in triples if x[0] == x[2]]) == 0:
        return False

    couples = _window(line, 2)
    for a in range(len(couples) - 1):
        for b in range(a + 2, len(couples)):
            if couples[a] == couples[b]:
                return True
    return False


def _window(s: str, size: int) -> list[str]:
    return [s[i:i+size] for i in range(len(s) - size + 1)]

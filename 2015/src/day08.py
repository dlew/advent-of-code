import re


def day8_part1(data: str) -> int:
    return sum([_decode_score(string) for string in data.splitlines()])


def day8_part2(data: str) -> int:
    return sum([_encode_score(string) for string in data.splitlines()])


_DECODE = re.compile(r"\\\"|\\\\|\\x[0-9a-f][0-9a-f]")
_ENCODE = re.compile(r"[\\\"]")


def _decode_score(string: str) -> int:
    return len(string) - len(_DECODE.sub('.', string)) + 2


def _encode_score(string: str) -> int:
    return len(_ENCODE.sub('..', string)) + 2 - len(string)
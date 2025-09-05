import json


def day12_part1(data: str) -> int:
    return _traverse(json.loads(data), False)


def day12_part2(data: str) -> int:
    return _traverse(json.loads(data), True)


def _traverse(obj: object, ignore_red: bool) -> int:
    if isinstance(obj, int):
        return obj

    if isinstance(obj, list):
        return sum([_traverse(element, ignore_red) for element in obj])

    if isinstance(obj, dict):
        if ignore_red and "red" in list(obj.keys()) + list(obj.values()):
            return 0

        return sum([_traverse(obj[element], ignore_red) for element in obj])

    return 0

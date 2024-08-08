import hashlib


def day4_part1(data: str) -> int:
    return _miner(data, "00000")


def day4_part2(data: str) -> int:
    return _miner(data, "000000")


def _miner(data: str, starts_with: str) -> int:
    number = 1
    while True:
        digest = hashlib.md5((data + str(number)).encode()).hexdigest()
        if digest.startswith(starts_with):
            return number
        number += 1

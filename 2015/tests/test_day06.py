from unittest import TestCase

from src.day06 import day6_part1, day6_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day6_part1_sample(self):
        instructions = [
            "turn on 0,0 through 999,999",
            "toggle 0,0 through 999,0",
            "turn off 499,499 through 500,500",
        ]
        self.assertEqual(998_996, day6_part1("\n".join(instructions)))

    def test_day6_part1(self):
        self.assertEqual(400_410, day6_part1(read_file("inputs/day06.txt")))

    def test_day6_part2_sample(self):
        instructions = [
            "turn on 0,0 through 0,0",
            "toggle 0,0 through 999,999",
        ]
        self.assertEqual(2_000_001, day6_part2("\n".join(instructions)))

    def test_day6_part2(self):
        self.assertEqual(15_343_601, day6_part2(read_file("inputs/day06.txt")))

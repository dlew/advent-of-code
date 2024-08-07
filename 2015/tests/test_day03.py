from unittest import TestCase

from src.day03 import day3_part1, day3_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day3_part1_samples(self):
        self.assertEqual(2, day3_part1(">"))
        self.assertEqual(4, day3_part1("^>v<"))
        self.assertEqual(2, day3_part1("^v^v^v^v^v"))

    def test_day3_part1(self):
        self.assertEqual(2572, day3_part1(read_file("inputs/day03.txt")))

    def test_day3_part2_samples(self):
        self.assertEqual(3, day3_part2("^v"))
        self.assertEqual(3, day3_part2("^>v<"))
        self.assertEqual(11, day3_part2("^v^v^v^v^v"))

    def test_day3_part2(self):
        self.assertEqual(2631, day3_part2(read_file("inputs/day03.txt")))

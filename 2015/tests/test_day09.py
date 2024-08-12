from unittest import TestCase

from src.day09 import day9_part1, day9_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day9_part1_sample(self):
        self.assertEqual(605, day9_part1(read_file("inputs/day09_sample.txt")))

    def test_day9_part1(self):
        self.assertEqual(251, day9_part1(read_file("inputs/day09.txt")))

    def test_day9_part2_sample(self):
        self.assertEqual(982, day9_part2(read_file("inputs/day09_sample.txt")))

    def test_day9_part2(self):
        self.assertEqual(898, day9_part2(read_file("inputs/day09.txt")))

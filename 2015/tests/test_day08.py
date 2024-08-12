from unittest import TestCase

from src.day08 import day8_part1, day8_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day8_part1_sample(self):
        self.assertEqual(12, day8_part1(read_file("inputs/day08_sample.txt")))

    def test_day8_part1(self):
        self.assertEqual(1350, day8_part1(read_file("inputs/day08.txt")))

    def test_day8_part2_sample(self):
        self.assertEqual(19, day8_part2(read_file("inputs/day08_sample.txt")))

    def test_day8_part2(self):
        self.assertEqual(2085, day8_part2(read_file("inputs/day08.txt")))
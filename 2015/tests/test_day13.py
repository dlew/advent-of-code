from unittest import TestCase

from src.day13 import day13_part1, day13_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day13_part1_sample(self):
        self.assertEqual(330, day13_part1(read_file("inputs/day13_sample.txt")))

    def test_day13_part1(self):
        self.assertEqual(664, day13_part1(read_file("inputs/day13.txt")))

    def test_day13_part2(self):
        self.assertEqual(640, day13_part2(read_file("inputs/day13.txt")))

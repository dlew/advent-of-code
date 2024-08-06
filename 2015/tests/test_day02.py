from unittest import TestCase

from src.day02 import day2_part1, day2_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day2_part1_sample(self):
        self.assertEqual(58, day2_part1("2x3x4"))
        self.assertEqual(43, day2_part1("1x1x10"))
        self.assertEqual(58 + 43, day2_part1("2x3x4\n1x1x10"))

    def test_day2_part1(self):
        self.assertEqual(1586300, day2_part1(read_file("inputs/day02.txt")))

    def test_day2_part2_sample(self):
        self.assertEqual(34, day2_part2("2x3x4"))
        self.assertEqual(14, day2_part2("1x1x10"))
        self.assertEqual(34 + 14, day2_part2("2x3x4\n1x1x10"))

    def test_day2_part2(self):
        self.assertEqual(3737498, day2_part2(read_file("inputs/day02.txt")))

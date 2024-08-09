from unittest import TestCase

from src.day07 import day7_part1, day7_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day7_part1_sample(self):
        output = day7_part1(read_file("inputs/day07_sample.txt"))
        self.assertEqual(72, output["d"])
        self.assertEqual(507, output["e"])
        self.assertEqual(492, output["f"])
        self.assertEqual(114, output["g"])
        self.assertEqual(65412, output["h"])
        self.assertEqual(65079, output["i"])
        self.assertEqual(123, output["x"])
        self.assertEqual(456, output["y"])

    def test_day7_part1(self):
        output = day7_part1(read_file("inputs/day07.txt"))
        self.assertEqual(3176, output["a"])

    def test_day7_part2(self):
        output = day7_part2(read_file("inputs/day07.txt"))
        self.assertEqual(14710, output["a"])

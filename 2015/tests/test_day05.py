from unittest import TestCase

from src.day05 import day5_part1, day5_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day5_part1_sample(self):
        self.assertEqual(1, day5_part1("ugknbfddgicrmopn"))
        self.assertEqual(1, day5_part1("aaa"))
        self.assertEqual(0, day5_part1("jchzalrnumimnmhp"))
        self.assertEqual(0, day5_part1("haegwjzuvuyypxyu"))
        self.assertEqual(0, day5_part1("dvszwmarrgswjxmb"))

    def test_day5_part1(self):
        self.assertEqual(258, day5_part1(read_file("inputs/day05.txt")))

    def test_day5_part2_sample(self):
        self.assertEqual(1, day5_part2("qjhvhtzxzqqjkmpb"))
        self.assertEqual(1, day5_part2("xxyxx"))
        self.assertEqual(0, day5_part2("uurcxstgmygtbstg"))
        self.assertEqual(0, day5_part2("ieodomkazucvgmuy"))

    def test_day5_part2(self):
        self.assertEqual(53, day5_part2(read_file("inputs/day05.txt")))

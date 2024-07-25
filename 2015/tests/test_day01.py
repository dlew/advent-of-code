from unittest import TestCase

from src.day01 import day1_part1, day1_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day1_part1_samples(self):
        self.assertEqual(0, day1_part1("(())"))
        self.assertEqual(0, day1_part1("()()"))
        self.assertEqual(3, day1_part1("((("))
        self.assertEqual(3, day1_part1("(()(()("))
        self.assertEqual(3, day1_part1("))((((("))
        self.assertEqual(-1, day1_part1("())"))
        self.assertEqual(-1, day1_part1("))("))
        self.assertEqual(-3, day1_part1(")))"))
        self.assertEqual(-3, day1_part1(")())())"))

    def test_day1_part1(self):
        self.assertEqual(280, day1_part1(read_file("inputs/day01.txt")))

    def test_day1_part2_samples(self):
        self.assertEqual(1, day1_part2(")"))
        self.assertEqual(5, day1_part2("()())"))

    def test_day1_part2(self):
        self.assertEqual(1797, day1_part2(read_file("inputs/day01.txt")))

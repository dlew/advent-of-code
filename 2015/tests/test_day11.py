from unittest import TestCase

from src.day11 import day11


class Test(TestCase):
    def test_day11_part1_sample(self):
        self.assertEqual("abcdffaa", day11("abcdefgh"))
        self.assertEqual("ghjaabcc", day11("ghijklmn"))

    def test_day11_part1(self):
        self.assertEqual("hxbxxyzz", day11("hxbxwxba"))

    def test_day11_part2(self):
        self.assertEqual("hxcaabcc", day11(day11("hxbxwxba")))

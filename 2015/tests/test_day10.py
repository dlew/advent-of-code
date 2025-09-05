from unittest import TestCase

from src.day10 import day10


class Test(TestCase):
    def test_day10_part1_sample(self):
        self.assertEqual(6, day10("1", 5))

    def test_day10_part1(self):
        self.assertEqual(492982, day10("1321131112", 40))

    def test_day10_part2(self):
        self.assertEqual(6989950, day10("1321131112", 50))

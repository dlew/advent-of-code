from unittest import TestCase

from src.day04 import day4_part1, day4_part2


class Test(TestCase):
    def test_day4_part1_sample(self):
        self.assertEqual(609043, day4_part1("abcdef"))
        self.assertEqual(1048970, day4_part1("pqrstuv"))

    def test_day4_part1(self):
        self.assertEqual(282749, day4_part1("yzbqklnj"))

    def test_day4_part2(self):
        self.assertEqual(9962624, day4_part2("yzbqklnj"))

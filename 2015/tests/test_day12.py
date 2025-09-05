from unittest import TestCase

from src.day12 import day12_part1, day12_part2
from tests.utils.read_file import read_file


class Test(TestCase):
    def test_day12_part1_sample(self):
        self.assertEqual(6, day12_part1("[1,2,3]"))
        self.assertEqual(6, day12_part1('{"a":2,"b":4}'))
        self.assertEqual(3, day12_part1("[[[3]]]"))
        self.assertEqual(3, day12_part1('{"a":{"b":4},"c":-1}'))
        self.assertEqual(0, day12_part1('{"a":[-1,1]}'))
        self.assertEqual(0, day12_part1('[-1,{"a":1}]'))
        self.assertEqual(0, day12_part1("[]"))
        self.assertEqual(0, day12_part1("{}"))

    def test_day12_part1(self):
        self.assertEqual(156366, day12_part1(read_file("inputs/day12.txt")))

    def test_day12_part2_sample(self):
        self.assertEqual(6, day12_part2("[1,2,3]"))
        self.assertEqual(4, day12_part2('[1,{"c":"red","b":2},3]'))
        self.assertEqual(0, day12_part2('{"d":"red","e":[1,2,3,4],"f":5}'))
        self.assertEqual(6, day12_part2('[1,"red",5]'))

    def test_day12_part2(self):
        self.assertEqual(96852, day12_part2(read_file("inputs/day12.txt")))

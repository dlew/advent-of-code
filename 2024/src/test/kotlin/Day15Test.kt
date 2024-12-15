import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day15Test {
  @Test
  fun part1_sample() {
    assertEquals(2028, Day15.part1(getResourceAsString("day15-sample.txt")))
    assertEquals(10092, Day15.part1(getResourceAsString("day15-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1509863, Day15.part1(getResourceAsString("day15.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(618, Day15.part2(getResourceAsString("day15-sample3.txt")))
    assertEquals(9021, Day15.part2(getResourceAsString("day15-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1548815, Day15.part2(getResourceAsString("day15.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day15Test {
  @Test
  fun part1_sample() {
    assertEquals(1320, Day15.part1(getResourceAsString("day15-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(515495, Day15.part1(getResourceAsString("day15.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(145, Day15.part2(getResourceAsString("day15-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(229349, Day15.part2(getResourceAsString("day15.txt")))
  }
}
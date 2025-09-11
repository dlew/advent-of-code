import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day04Test {
  @Test
  fun part1_sample() {
    assertEquals(2, Day04.part1(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(235, Day04.part1(getResourceAsString("day04.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(4, Day04.part2(getResourceAsString("day04-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(194, Day04.part2(getResourceAsString("day04.txt")))
  }
}
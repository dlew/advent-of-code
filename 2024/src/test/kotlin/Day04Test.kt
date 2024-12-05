import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day04Test {
  @Test
  fun part1_sample() {
    assertEquals(18, Day04.part1(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(2464, Day04.part1(getResourceAsString("day04.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(9, Day04.part2(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1982, Day04.part2(getResourceAsString("day04.txt")))
  }
}
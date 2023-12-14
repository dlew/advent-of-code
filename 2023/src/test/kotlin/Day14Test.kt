import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day14Test {
  @Test
  fun part1_sample() {
    assertEquals(136, Day14.part1(getResourceAsString("day14-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(111979, Day14.part1(getResourceAsString("day14.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(64, Day14.part2(getResourceAsString("day14-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(102055, Day14.part2(getResourceAsString("day14.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day08Test {
  @Test
  fun part1_sample() {
    assertEquals(5, Day08.part1(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1331, Day08.part1(getResourceAsString("day08.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(8, Day08.part2(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1121, Day08.part2(getResourceAsString("day08.txt")))
  }
}
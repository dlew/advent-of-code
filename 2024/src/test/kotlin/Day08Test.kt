import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day08Test {
  @Test
  fun part1_sample() {
    assertEquals(14, Day08.part1(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(249, Day08.part1(getResourceAsString("day08.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(34, Day08.part2(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(905, Day08.part2(getResourceAsString("day08.txt")))
  }
}
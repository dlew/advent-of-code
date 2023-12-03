import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day03Test {
  @Test
  fun part1_sample() {
    assertEquals(4361, Day03.part1(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(550064, Day03.part1(getResourceAsString("day03.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(467835, Day03.part2(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(85010461, Day03.part2(getResourceAsString("day03.txt")))
  }
}
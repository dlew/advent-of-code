import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day03Test {
  @Test
  fun part1_sample() {
    assertEquals(7L, Day03.part1(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(234L, Day03.part1(getResourceAsString("day03.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(336L, Day03.part2(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(5813773056L, Day03.part2(getResourceAsString("day03.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day03Test {
  @Test
  fun part1_sample() {
    assertEquals(357, Day03.part1(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(17109, Day03.part1(getResourceAsString("day03.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(3121910778619, Day03.part2(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(169347417057382, Day03.part2(getResourceAsString("day03.txt")))
  }

}
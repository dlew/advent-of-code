import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day03Test {
  @Test
  fun part1_sample() {
    assertEquals(161, Day03.part1(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(175700056, Day03.part1(getResourceAsString("day03.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(48, Day03.part2(getResourceAsString("day03-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(71668682, Day03.part2(getResourceAsString("day03.txt")))
  }
}
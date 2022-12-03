import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day03Test {
  @Test
  fun part1_sample() {
    assertEquals(157, Day03.part1(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(7997, Day03.part1(getResourceAsString("day03.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(70, Day03.part2(getResourceAsString("day03-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2545, Day03.part2(getResourceAsString("day03.txt")))
  }
}
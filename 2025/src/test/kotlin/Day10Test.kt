import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day10Test {
  @Test
  fun part1_sample() {
    assertEquals(7, Day10.part1(getResourceAsString("day10-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(419, Day10.part1(getResourceAsString("day10.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(33, Day10.part2(getResourceAsString("day10-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(18369, Day10.part2(getResourceAsString("day10.txt")))
  }
}
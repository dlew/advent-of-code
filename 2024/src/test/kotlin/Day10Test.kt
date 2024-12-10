import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day10Test {
  @Test
  fun part1_sample() {
    assertEquals(36, Day10.part1(getResourceAsString("day10-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(582, Day10.part1(getResourceAsString("day10.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(81, Day10.part2(getResourceAsString("day10-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1302, Day10.part2(getResourceAsString("day10.txt")))
  }
}
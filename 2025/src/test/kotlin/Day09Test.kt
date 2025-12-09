import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day09Test {
  @Test
  fun part1_sample() {
    assertEquals(50, Day09.part1(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(4740155680, Day09.part1(getResourceAsString("day09.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(24, Day09.part2(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1543501936, Day09.part2(getResourceAsString("day09.txt")))
  }
}
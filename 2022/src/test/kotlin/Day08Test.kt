import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day08Test {

  @Test
  fun part1_sample() {
    assertEquals(21, Day08.part1(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1854, Day08.part1(getResourceAsString("day08.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(8, Day08.part2(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(527340, Day08.part2(getResourceAsString("day08.txt")))
  }

}
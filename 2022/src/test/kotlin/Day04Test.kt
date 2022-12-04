import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day04Test {
  @Test
  fun part1_sample() {
    assertEquals(2, Day04.part1(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(550, Day04.part1(getResourceAsString("day04.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(4, Day04.part2(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(931, Day04.part2(getResourceAsString("day04.txt")))
  }
}
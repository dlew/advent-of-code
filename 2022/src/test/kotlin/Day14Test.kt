import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day14Test {

  @Test
  fun part1_sample() {
    assertEquals(24, Day14.part1(getResourceAsString("day14-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(683, Day14.part1(getResourceAsString("day14.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(93, Day14.part2(getResourceAsString("day14-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(28821, Day14.part2(getResourceAsString("day14.txt")))
  }

}
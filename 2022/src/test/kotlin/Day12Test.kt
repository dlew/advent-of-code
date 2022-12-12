import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day12Test {

  @Test
  fun part1_sample() {
    assertEquals(31, Day12.part1(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(423, Day12.part1(getResourceAsString("day12.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(29, Day12.part2(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(416, Day12.part2(getResourceAsString("day12.txt")))
  }
  
}
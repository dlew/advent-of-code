import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day16Test {

  @Test
  fun part1_sample() {
    assertEquals(1651, Day16.part1(getResourceAsString("day16-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(2320, Day16.part1(getResourceAsString("day16.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(1707, Day16.part2(getResourceAsString("day16-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2967, Day16.part2(getResourceAsString("day16.txt")))
  }

}
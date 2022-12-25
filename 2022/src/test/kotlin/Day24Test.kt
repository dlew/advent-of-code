import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day24Test {

  @Test
  fun part1_sample() {
    assertEquals(18, Day24.part1(getResourceAsString("day24-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(249, Day24.part1(getResourceAsString("day24.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(54, Day24.part2(getResourceAsString("day24-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(735, Day24.part2(getResourceAsString("day24.txt")))
  }

}
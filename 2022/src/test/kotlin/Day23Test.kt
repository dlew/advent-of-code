import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day23Test {

  @Test
  fun part1_mini_sample() {
    assertEquals(25, Day23.part1(getResourceAsString("day23-mini-sample.txt")))
  }

  @Test
  fun part1_sample() {
    assertEquals(110, Day23.part1(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(3931, Day23.part1(getResourceAsString("day23.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(20, Day23.part2(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(944, Day23.part2(getResourceAsString("day23.txt")))
  }

}
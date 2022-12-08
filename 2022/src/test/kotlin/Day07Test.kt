import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day07Test {

  @Test
  fun part1_sample() {
    assertEquals(95437, Day07.part1(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1642503, Day07.part1(getResourceAsString("day07.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(24933642, Day07.part2(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(6999588, Day07.part2(getResourceAsString("day07.txt")))
  }

}
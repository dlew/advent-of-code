import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day07Test {
  @Test
  fun part1_sample() {
    assertEquals(4, Day07.part1(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(128, Day07.part1(getResourceAsString("day07.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(32, Day07.part2(getResourceAsString("day07-sample.txt")))
    assertEquals(126, Day07.part2(getResourceAsString("day07-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(20189, Day07.part2(getResourceAsString("day07.txt")))
  }
}
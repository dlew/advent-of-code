import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day07Test {
  @Test
  fun part1_sample() {
    assertEquals(6440, Day07.part1(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(248559379, Day07.part1(getResourceAsString("day07.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(5905, Day07.part2(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(249631254, Day07.part2(getResourceAsString("day07.txt")))
  }
}
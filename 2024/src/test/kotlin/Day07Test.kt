import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day07Test {
  @Test
  fun part1_sample() {
    assertEquals(3749L, Day07.part1(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1985268524462L, Day07.part1(getResourceAsString("day07.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(11387L, Day07.part2(getResourceAsString("day07-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(150077710195188L, Day07.part2(getResourceAsString("day07.txt")))
  }
}
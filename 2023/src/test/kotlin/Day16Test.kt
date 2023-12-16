import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day16Test {
  @Test
  fun part1_sample() {
    assertEquals(46, Day16.part1(getResourceAsString("day16-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(6622, Day16.part1(getResourceAsString("day16.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(51, Day16.part2(getResourceAsString("day16-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(7130, Day16.part2(getResourceAsString("day16.txt")))
  }
}
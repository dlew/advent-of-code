import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day04Test {
  @Test
  fun part1_sample() {
    assertEquals(13, Day04.part1(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1537, Day04.part1(getResourceAsString("day04.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(43, Day04.part2(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(8707, Day04.part2(getResourceAsString("day04.txt")))
  }
}
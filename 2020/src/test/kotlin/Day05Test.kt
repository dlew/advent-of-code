import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day05Test {
  @Test
  fun part1_sample() {
    assertEquals(820, Day05.part1(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(835, Day05.part1(getResourceAsString("day05.txt")))
  }

  @Test
  fun part2() {
    assertEquals(649, Day05.part2(getResourceAsString("day05.txt")))
  }
}
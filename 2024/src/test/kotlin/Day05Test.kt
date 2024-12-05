import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day05Test {
  @Test
  fun part1_sample() {
    assertEquals(143, Day05.part1(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(5955, Day05.part1(getResourceAsString("day05.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(123, Day05.part2(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(4030, Day05.part2(getResourceAsString("day05.txt")))
  }
}
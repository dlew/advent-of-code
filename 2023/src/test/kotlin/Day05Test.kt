import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day05Test {
  @Test
  fun part1_sample() {
    assertEquals(35, Day05.part1(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(107430936, Day05.part1(getResourceAsString("day05.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(46, Day05.part2(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(23738616, Day05.part2(getResourceAsString("day05.txt")))
  }
}
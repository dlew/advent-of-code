import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day19Test {
  @Test
  fun part1_sample() {
    assertEquals(6, Day19.part1(getResourceAsString("day19-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(367, Day19.part1(getResourceAsString("day19.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(16L, Day19.part2(getResourceAsString("day19-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(724388733465031L, Day19.part2(getResourceAsString("day19.txt")))
  }
}
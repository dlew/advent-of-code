import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day11Test {
  @Test
  fun part1_sample() {
    assertEquals(5, Day11.part1(getResourceAsString("day11-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(670, Day11.part1(getResourceAsString("day11.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(2, Day11.part2(getResourceAsString("day11-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(332052564714990, Day11.part2(getResourceAsString("day11.txt")))
  }
}
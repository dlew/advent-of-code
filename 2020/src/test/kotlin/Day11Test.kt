import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day11Test {
  @Test
  fun part1_sample() {
    assertEquals(37, Day11.part1(getResourceAsString("day11-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(2427, Day11.part1(getResourceAsString("day11.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(26, Day11.part2(getResourceAsString("day11-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2199, Day11.part2(getResourceAsString("day11.txt")))
  }
}
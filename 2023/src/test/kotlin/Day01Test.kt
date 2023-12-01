import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day01Test {
  @Test
  fun part1_sample() {
    assertEquals(142, Day01.part1(getResourceAsString("day01-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(54953, Day01.part1(getResourceAsString("day01.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(281, Day01.part2(getResourceAsString("day01-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(53868, Day01.part2(getResourceAsString("day01.txt")))
  }
}
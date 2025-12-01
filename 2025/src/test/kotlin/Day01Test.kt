import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day01Test {
  @Test
  fun part1_sample() {
    assertEquals(3, Day01.part1(getResourceAsString("day01-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1021, Day01.part1(getResourceAsString("day01.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(6, Day01.part2(getResourceAsString("day01-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(5933, Day01.part2(getResourceAsString("day01.txt")))
  }

}
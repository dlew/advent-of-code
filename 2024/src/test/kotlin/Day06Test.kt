import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day06Test {
  @Test
  fun part1_sample() {
    assertEquals(41, Day06.part1(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(5564, Day06.part1(getResourceAsString("day06.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(6, Day06.part2(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(1976, Day06.part2(getResourceAsString("day06.txt")))
  }
}
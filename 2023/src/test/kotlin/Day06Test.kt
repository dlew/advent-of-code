import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day06Test {
  @Test
  fun part1_sample() {
    assertEquals(288, Day06.part1(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(440000, Day06.part1(getResourceAsString("day06.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(71503, Day06.part2(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(26187338, Day06.part2(getResourceAsString("day06.txt")))
  }
}
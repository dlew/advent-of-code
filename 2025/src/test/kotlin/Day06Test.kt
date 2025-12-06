import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day06Test {
  @Test
  fun part1_sample() {
    assertEquals(4277556, Day06.part1(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(4076006202939, Day06.part1(getResourceAsString("day06.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(3263827, Day06.part2(getResourceAsString("day06-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(7903168391557, Day06.part2(getResourceAsString("day06.txt")))
  }
}
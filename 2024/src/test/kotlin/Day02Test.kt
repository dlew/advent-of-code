import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day02Test {
  @Test
  fun part1_sample() {
    assertEquals(2, Day02.part1(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(359, Day02.part1(getResourceAsString("day02.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(4, Day02.part2(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(418, Day02.part2(getResourceAsString("day02.txt")))
  }
}
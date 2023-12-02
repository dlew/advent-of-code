import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day02Test {
  @Test
  fun part1_sample() {
    assertEquals(8, Day02.part1(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(2486, Day02.part1(getResourceAsString("day02.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(2286, Day02.part2(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(87984, Day02.part2(getResourceAsString("day02.txt")))
  }
}
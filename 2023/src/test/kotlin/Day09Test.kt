import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day09Test {
  @Test
  fun part1_sample() {
    assertEquals(114, Day09.part1(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1641934234, Day09.part1(getResourceAsString("day09.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(2, Day09.part2(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(975, Day09.part2(getResourceAsString("day09.txt")))
  }
}
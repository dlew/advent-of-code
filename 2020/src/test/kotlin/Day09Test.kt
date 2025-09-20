import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day09Test {
  @Test
  fun part1_sample() {
    assertEquals(127, Day09.part1(getResourceAsString("day09-sample.txt"), 5))
  }

  @Test
  fun part1() {
    assertEquals(31161678, Day09.part1(getResourceAsString("day09.txt"), 25))
  }

  @Test
  fun part2_sample() {
    assertEquals(62, Day09.part2(getResourceAsString("day09-sample.txt"), 5))
  }

  @Test
  fun part2() {
    assertEquals(5453868, Day09.part2(getResourceAsString("day09.txt"), 25))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day18Test {
  @Test
  fun part1_sample() {
    assertEquals(22, Day18.part1(getResourceAsString("day18-sample.txt"), 7, 12))
  }

  @Test
  fun part1() {
    assertEquals(232, Day18.part1(getResourceAsString("day18.txt"), 71, 1024))
  }

  @Test
  fun part2_sample() {
    assertEquals("6,1", Day18.part2(getResourceAsString("day18-sample.txt"), 7, 12))
  }

  @Test
  fun part2() {
    assertEquals("44,64", Day18.part2(getResourceAsString("day18.txt"), 71, 1024))
  }
}
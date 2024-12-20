import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day20Test {
  @Test
  fun part1_sample() {
    assertEquals(10, Day20.part1(getResourceAsString("day20-sample.txt"), 10))
  }

  @Test
  fun part1() {
    assertEquals(1360, Day20.part1(getResourceAsString("day20.txt"), 100))
  }

  @Test
  fun part2_sample() {
    assertEquals(41, Day20.part2(getResourceAsString("day20-sample.txt"), 70))
  }

  @Test
  fun part2() {
    assertEquals(1005476, Day20.part2(getResourceAsString("day20.txt"), 100))
  }
}
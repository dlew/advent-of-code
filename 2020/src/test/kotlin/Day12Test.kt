import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day12Test {
  @Test
  fun part1_sample() {
    assertEquals(25, Day12.part1(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(508, Day12.part1(getResourceAsString("day12.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(286, Day12.part2(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(30761, Day12.part2(getResourceAsString("day12.txt")))
  }
}
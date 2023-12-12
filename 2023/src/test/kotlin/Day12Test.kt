import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day12Test {
  @Test
  fun part1_sample() {
    assertEquals(21, Day12.part1(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(8180, Day12.part1(getResourceAsString("day12.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(525152, Day12.part2(getResourceAsString("day12-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(620189727003627, Day12.part2(getResourceAsString("day12.txt")))
  }
}
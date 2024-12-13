import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day12Test {
  @Test
  fun part1_sample() {
    assertEquals(140, Day12.part1(getResourceAsString("day12-sample.txt")))
    assertEquals(772, Day12.part1(getResourceAsString("day12-sample2.txt")))
    assertEquals(1930, Day12.part1(getResourceAsString("day12-sample3.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1494342, Day12.part1(getResourceAsString("day12.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(80, Day12.part2(getResourceAsString("day12-sample.txt")))
    assertEquals(436, Day12.part2(getResourceAsString("day12-sample2.txt")))
    assertEquals(1206, Day12.part2(getResourceAsString("day12-sample3.txt")))
    assertEquals(236, Day12.part2(getResourceAsString("day12-sample4.txt")))
    assertEquals(368, Day12.part2(getResourceAsString("day12-sample5.txt")))
  }

  @Test
  fun part2() {
    assertEquals(893676, Day12.part2(getResourceAsString("day12.txt")))
  }
}
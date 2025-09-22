import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day10Test {
  @Test
  fun part1_sample() {
    assertEquals(35, Day10.part1(getResourceAsString("day10-sample.txt")))
    assertEquals(220, Day10.part1(getResourceAsString("day10-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1980, Day10.part1(getResourceAsString("day10.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(8, Day10.part2(getResourceAsString("day10-sample.txt")))
    assertEquals(19208, Day10.part2(getResourceAsString("day10-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(4628074479616L, Day10.part2(getResourceAsString("day10.txt")))
  }
}
import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day18Test {

  @Test
  fun part1_sample() {
    assertEquals(64, Day18.part1(getResourceAsString("day18-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(4628, Day18.part1(getResourceAsString("day18.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(58, Day18.part2(getResourceAsString("day18-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2582, Day18.part2(getResourceAsString("day18.txt")))
  }

}
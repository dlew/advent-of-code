import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day18Test {
  @Test
  fun part1_sample() {
    assertEquals(62, Day18.part1(getResourceAsString("day18-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(40761, Day18.part1(getResourceAsString("day18.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(952408144115L, Day18.part2(getResourceAsString("day18-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(106920098354636L, Day18.part2(getResourceAsString("day18.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day10Test {
  @Test
  fun part1_sample1() {
    assertEquals(4, Day10.part1(getResourceAsString("day10-sample1.txt")))
  }

  @Test
  fun part1_sample2() {
    assertEquals(8, Day10.part1(getResourceAsString("day10-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(6823, Day10.part1(getResourceAsString("day10.txt")))
  }

  @Test
  fun part2_sample1() {
    assertEquals(4, Day10.part2(getResourceAsString("day10-sample3.txt")))
  }

  @Test
  fun part2_sample2() {
    assertEquals(4, Day10.part2(getResourceAsString("day10-sample4.txt")))
  }

  @Test
  fun part2_sample3() {
    assertEquals(8, Day10.part2(getResourceAsString("day10-sample5.txt")))
  }

  @Test
  fun part2_sample4() {
    assertEquals(10, Day10.part2(getResourceAsString("day10-sample6.txt")))
  }

  @Test
  fun part2() {
    assertEquals(415, Day10.part2(getResourceAsString("day10.txt")))
  }
}
import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day09Test {

  @Test
  fun part1_sample() {
    assertEquals(13, Day09.part1(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(5883, Day09.part1(getResourceAsString("day09.txt")))
  }

  @Test
  fun part2_sample1() {
    assertEquals(1, Day09.part2(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part2_sample2() {
    assertEquals(36, Day09.part2(getResourceAsString("day09-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2367, Day09.part2(getResourceAsString("day09.txt")))
  }

}
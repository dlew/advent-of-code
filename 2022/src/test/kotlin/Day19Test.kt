import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day19Test {

  @Test
  fun part1_sample() {
    assertEquals(33, Day19.part1(getResourceAsString("day19-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1023, Day19.part1(getResourceAsString("day19.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(3472, Day19.part2(getResourceAsString("day19-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(13520, Day19.part2(getResourceAsString("day19.txt")))
  }

}
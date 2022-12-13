import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day13Test {

  @Test
  fun part1_sample() {
    assertEquals(13, Day13.part1(getResourceAsString("day13-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(6415, Day13.part1(getResourceAsString("day13.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(140, Day13.part2(getResourceAsString("day13-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(20056, Day13.part2(getResourceAsString("day13.txt")))
  }

}
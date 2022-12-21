import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day20Test {

  @Test
  fun part1_sample() {
    assertEquals(3L, Day20.part1(getResourceAsString("day20-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(4578L, Day20.part1(getResourceAsString("day20.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(1623178306L, Day20.part2(getResourceAsString("day20-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2159638736133L, Day20.part2(getResourceAsString("day20.txt")))
  }

}
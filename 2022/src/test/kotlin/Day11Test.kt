import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day11Test {

  @Test
  fun part1_sample() {
    assertEquals(10605L, Day11.part1(getResourceAsString("day11-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(108240L, Day11.part1(getResourceAsString("day11.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(2713310158, Day11.part2(getResourceAsString("day11-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(25712998901L, Day11.part2(getResourceAsString("day11.txt")))
  }

}
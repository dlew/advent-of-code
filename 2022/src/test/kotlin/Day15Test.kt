import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day15Test {

  @Test
  fun part1_sample() {
    assertEquals(26, Day15.part1(getResourceAsString("day15-sample.txt"), 10))
  }

  @Test
  fun part1() {
    assertEquals(5112034, Day15.part1(getResourceAsString("day15.txt"), 2_000_000))
  }

  @Test
  fun part2_sample() {
    assertEquals(56000011L, Day15.part2(getResourceAsString("day15-sample.txt"), 20))
  }

  @Test
  fun part2() {
    assertEquals(13172087230812L, Day15.part2(getResourceAsString("day15.txt"), 4_000_000))
  }

}
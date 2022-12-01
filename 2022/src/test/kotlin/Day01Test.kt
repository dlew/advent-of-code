import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day01Test {
  @Test
  fun part1_sample() {
    assertEquals(24000, Day01.topElf(getResourceAsString("day01-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(67016, Day01.topElf(getResourceAsString("day01.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(45000, Day01.topThreeElves(getResourceAsString("day01-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(200116, Day01.topThreeElves(getResourceAsString("day01.txt")))
  }
}
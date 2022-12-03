import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day02Test {
  @Test
  fun part1_sample() {
    assertEquals(15, Day02.totalScore(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(14069, Day02.totalScore(getResourceAsString("day02.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(12, Day02.totalScoreWithStrategy(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(12411, Day02.totalScoreWithStrategy(getResourceAsString("day02.txt")))
  }
}
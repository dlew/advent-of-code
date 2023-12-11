import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day11Test {
  @Test
  fun part1_sample() {
    assertEquals(374, Day11.sumPairwisePaths(getResourceAsString("day11-sample.txt"), 2))
  }

  @Test
  fun part1() {
    assertEquals(9918828, Day11.sumPairwisePaths(getResourceAsString("day11.txt"), 2))
  }

  @Test
  fun part2_sample1() {
    assertEquals(1030, Day11.sumPairwisePaths(getResourceAsString("day11-sample.txt"), 10))
  }

  @Test
  fun part2_sample2() {
    assertEquals(8410, Day11.sumPairwisePaths(getResourceAsString("day11-sample.txt"), 100))
  }

  @Test
  fun part2() {
    assertEquals(692506533832L, Day11.sumPairwisePaths(getResourceAsString("day11.txt"), 1000000))
  }
}
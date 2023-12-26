import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day23Test {
  @Test
  fun part1_sample() {
    assertEquals(94, Day23.part1(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(2306, Day23.part1(getResourceAsString("day23.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(154, Day23.part2(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(6718, Day23.part2(getResourceAsString("day23.txt")))
  }
}
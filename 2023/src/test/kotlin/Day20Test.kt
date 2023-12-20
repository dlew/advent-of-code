import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day20Test {
  @Test
  fun part1_sample1() {
    assertEquals(32000000, Day20.part1(getResourceAsString("day20-sample1.txt")))
  }

  @Test
  fun part1_sample2() {
    assertEquals(11687500, Day20.part1(getResourceAsString("day20-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(788848550, Day20.part1(getResourceAsString("day20.txt")))
  }

  @Test
  fun part2() {
    assertEquals(228300182686739, Day20.part2(getResourceAsString("day20.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day08Test {
  @Test
  fun part1_sample() {
    assertEquals(40, Day08.part1(getResourceAsString("day08-sample.txt"), 10))
  }

  @Test
  fun part1() {
    assertEquals(90036, Day08.part1(getResourceAsString("day08.txt"), 1000))
  }

  @Test
  fun part2_sample() {
    assertEquals(25272, Day08.part2(getResourceAsString("day08-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(6083499488, Day08.part2(getResourceAsString("day08.txt")))
  }
}
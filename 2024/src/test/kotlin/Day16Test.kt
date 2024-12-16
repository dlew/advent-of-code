import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day16Test {
  @Test
  fun part1_sample() {
    assertEquals(7036, Day16.part1(getResourceAsString("day16-sample.txt")))
    assertEquals(11048, Day16.part1(getResourceAsString("day16-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(102504, Day16.part1(getResourceAsString("day16.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(45, Day16.part2(getResourceAsString("day16-sample.txt")))
    assertEquals(64, Day16.part2(getResourceAsString("day16-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(535, Day16.part2(getResourceAsString("day16.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day13Test {
  @Test
  fun part1_sample() {
    assertEquals(405, Day13.part1(getResourceAsString("day13-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(33780, Day13.part1(getResourceAsString("day13.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(400, Day13.part2(getResourceAsString("day13-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(23479, Day13.part2(getResourceAsString("day13.txt")))
  }
}
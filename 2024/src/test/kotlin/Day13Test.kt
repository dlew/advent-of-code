import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day13Test {
  @Test
  fun part1_sample() {
    assertEquals(480, Day13.part1(getResourceAsString("day13-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(36954, Day13.part1(getResourceAsString("day13.txt")))
  }

  @Test
  fun part2() {
    assertEquals(79352015273424L, Day13.part2(getResourceAsString("day13.txt")))
  }
}
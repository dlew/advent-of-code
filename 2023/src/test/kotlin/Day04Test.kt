import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day04Test {
  @Test
  fun part1_sample() {
    assertEquals(13, Day04.part1(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(20117, Day04.part1(getResourceAsString("day04.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(30, Day04.part2(getResourceAsString("day04-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(13768818, Day04.part2(getResourceAsString("day04.txt")))
  }
}
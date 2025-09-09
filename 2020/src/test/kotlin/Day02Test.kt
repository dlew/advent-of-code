import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day02Test {
  @Test
  fun part1_sample() {
    assertEquals(2, Day02.part1(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(378, Day02.part1(getResourceAsString("day02.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(1, Day02.part2(getResourceAsString("day02-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(280, Day02.part2(getResourceAsString("day02.txt")))
  }
}
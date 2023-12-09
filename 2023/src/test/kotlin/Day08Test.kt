import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day08Test {
  @Test
  fun part1_sample1() {
    assertEquals(2, Day08.part1(getResourceAsString("day08-sample1.txt")))
  }

  @Test
  fun part1_sample2() {
    assertEquals(6, Day08.part1(getResourceAsString("day08-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(12599, Day08.part1(getResourceAsString("day08.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(6L, Day08.part2(getResourceAsString("day08-sample3.txt")))
  }

  @Test
  fun part2() {
    assertEquals(8245452805243L, Day08.part2(getResourceAsString("day08.txt")))
  }
}
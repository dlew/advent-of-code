import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day24Test {
  @Test
  fun part1_sample() {
    assertEquals(2, Day24.part1(getResourceAsString("day24-sample.txt"), 7L..27))
  }

  @Test
  fun part1() {
    assertEquals(12343, Day24.part1(getResourceAsString("day24.txt"), 200000000000000..400000000000000))
  }

  @Test
  fun part2() {
    assertEquals(769281292688187L, Day24.part2(getResourceAsString("day24.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day21Test {
  @Test
  fun part1_sample() {
    assertEquals(16, Day21.part1(getResourceAsString("day21-sample.txt"), 6))
  }

  @Test
  fun part1() {
    assertEquals(3716, Day21.part1(getResourceAsString("day21.txt"), 64))
  }

  @Test
  fun part2_sample() {
    assertEquals(94353, Day21.part2(getResourceAsString("day21.txt"), 327))
  }

  @Test
  fun part2() {
    assertEquals(616583483179597, Day21.part2(getResourceAsString("day21.txt"), 26501365))
  }
}
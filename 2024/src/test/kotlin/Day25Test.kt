import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day25Test {
  @Test
  fun part1_sample() {
    assertEquals(3, Day25.solve(getResourceAsString("day25-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(3619, Day25.solve(getResourceAsString("day25.txt")))
  }
}
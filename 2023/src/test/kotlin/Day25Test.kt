import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day25Test {
  @Test
  fun part1_sample() {
    assertEquals(54, Day25.part1(getResourceAsString("day25-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(602151, Day25.part1(getResourceAsString("day25.txt")))
  }
}
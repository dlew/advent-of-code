import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day21Test {
  @Test
  fun part1_sample() {
    assertEquals(126384, Day21.part1(getResourceAsString("day21-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(128962, Day21.part1(getResourceAsString("day21.txt")))
  }

  @Test
  fun part2() {
    assertEquals(159684145150108L, Day21.part2(getResourceAsString("day21.txt")))
  }
}
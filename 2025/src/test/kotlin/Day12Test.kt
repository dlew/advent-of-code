import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day12Test {
  @Test
  fun part1() {
    assertEquals(526, Day12.part1(getResourceAsString("day12.txt")))
  }
}
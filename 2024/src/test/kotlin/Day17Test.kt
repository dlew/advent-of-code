import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day17Test {
  @Test
  fun part1() {
    assertEquals("4,3,2,6,4,5,3,2,4", Day17.part1())
  }

  @Test
  fun part2() {
    assertEquals(164540892147389L, Day17.part2())
  }
}
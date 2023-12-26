import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day22Test {
  @Test
  fun part1_sample() {
    assertEquals(5, Day22.part1(getResourceAsString("day22-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(389, Day22.part1(getResourceAsString("day22.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(7, Day22.part2(getResourceAsString("day22-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(70609, Day22.part2(getResourceAsString("day22.txt")))
  }
}
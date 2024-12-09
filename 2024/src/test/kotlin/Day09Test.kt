import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day09Test {
  @Test
  fun part1_sample() {
    assertEquals(1928L, Day09.part1(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(6359213660505L, Day09.part1(getResourceAsString("day09.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(2858L, Day09.part2(getResourceAsString("day09-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(6381624803796L, Day09.part2(getResourceAsString("day09.txt")))
  }
}
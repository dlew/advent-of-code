import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day17Test {
  @Test
  fun part1_sample() {
    assertEquals(102, Day17.part1(getResourceAsString("day17-sample1.txt")))
  }

  @Test
  fun part1() {
    assertEquals(771, Day17.part1(getResourceAsString("day17.txt")))
  }

  @Test
  fun part2_sample1() {
    assertEquals(94, Day17.part2(getResourceAsString("day17-sample1.txt")))
  }

  @Test
  fun part2_sample2() {
    assertEquals(71, Day17.part2(getResourceAsString("day17-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(930, Day17.part2(getResourceAsString("day17.txt")))
  }
}
import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day22Test {
  @Test
  fun part1_sample() {
    assertEquals(37327623L, Day22.part1(getResourceAsString("day22-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(18694566361L, Day22.part1(getResourceAsString("day22.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(23, Day22.part2(getResourceAsString("day22-sample2.txt")))
  }

  @Test
  fun part2() {
    assertEquals(2100, Day22.part2(getResourceAsString("day22.txt")))
  }
}
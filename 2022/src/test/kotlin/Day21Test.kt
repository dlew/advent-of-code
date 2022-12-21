import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day21Test {

  @Test
  fun part1_sample() {
    assertEquals(152L, Day21.part1(getResourceAsString("day21-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(43699799094202L, Day21.part1(getResourceAsString("day21.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(301L, Day21.part2(getResourceAsString("day21-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals(3375719472770L, Day21.part2(getResourceAsString("day21.txt")))
  }

}
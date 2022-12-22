import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day22Test {

  @Test
  fun part1_sample() {
    assertEquals(6032, Day22.part1(getResourceAsString("day22-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(133174, Day22.part1(getResourceAsString("day22.txt")))
  }

  @Test
  fun part2_sample() {
    // Different answer than the actual sample because I fit the cube to the same shape as the actual input
    assertEquals(6022, Day22.part2(getResourceAsString("day22-sample-formatted.txt")))
  }

  @Test
  fun part2() {
     assertEquals(15410, Day22.part2(getResourceAsString("day22-formatted.txt")))
  }
}
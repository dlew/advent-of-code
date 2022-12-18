import org.junit.Assert.*
import org.junit.Test
import utils.getResourceAsString

class Day17Test {

  @Test
  fun part1_sample() {
    assertEquals(3068, Day17.part1(getResourceAsString("day17-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(3092, Day17.part1(getResourceAsString("day17.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(1514285714288L, Day17.part2(getResourceAsString("day17-sample.txt"), 1000, 10))
  }

  @Test
  fun part2() {
    // These starting values are based on my answer, but you can start with a much lower cycle size and still get the right answer
    assertEquals(1528323699442L, Day17.part2(getResourceAsString("day17.txt"), 4000, 1700))
  }

}
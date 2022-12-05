import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day05Test {

  @Test
  fun part1_sample() {
    assertEquals("CMZ", Day05.part1(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals("SHQWSRBDL", Day05.part1(getResourceAsString("day05.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals("MCD", Day05.part2(getResourceAsString("day05-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals("CDTQZHBRS", Day05.part2(getResourceAsString("day05.txt")))
  }

}
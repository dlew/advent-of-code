import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.Ignore
import kotlin.test.assertEquals

class Day14Test {
  @Test
  fun part1_sample() {
    assertEquals(12, Day14.part1(getResourceAsString("day14-sample.txt"), 11, 7))
  }

  @Test
  fun part1() {
    assertEquals(225521010, Day14.part1(getResourceAsString("day14.txt"), 101, 103))
  }

  @Test
  @Ignore // We don't want this running as part of our overall suite, only on demand
  fun part2() {
    // Instead of testing for correctness, this just outputs a bunch of images (of which time 7774 is correct)
    Day14.part2(getResourceAsString("day14.txt"))
  }
}
import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day06Test {

  @Test
  fun part1_samples() {
    assertEquals(7, Day06.part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
    assertEquals(5, Day06.part1("bvwbjplbgvbhsrlpgdmjqwftvncz"))
    assertEquals(6, Day06.part1("nppdvjthqldpwncqszvftbrmjlhg"))
    assertEquals(10, Day06.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
    assertEquals(11, Day06.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
  }

  @Test
  fun part1() {
    assertEquals(1343, Day06.part1(getResourceAsString("day06.txt")))
  }

  @Test
  fun part2_samples() {
    assertEquals(19, Day06.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
    assertEquals(23, Day06.part2("bvwbjplbgvbhsrlpgdmjqwftvncz"))
    assertEquals(23, Day06.part2("nppdvjthqldpwncqszvftbrmjlhg"))
    assertEquals(29, Day06.part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
    assertEquals(26, Day06.part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
  }

  @Test
  fun part2() {
    assertEquals(2193, Day06.part2(getResourceAsString("day06.txt")))
  }

}
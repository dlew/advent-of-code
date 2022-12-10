import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day10Test {

  @Test
  fun part1_sample() {
    assertEquals(13140, Day10.part1(getResourceAsString("day10-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(14060, Day10.part1(getResourceAsString("day10.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals(
      """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
      """.trimIndent(),
      Day10.part2(getResourceAsString("day10-sample.txt"))
    )
  }

  @Test
  fun part2() {
    assertEquals(
      """
        ###...##..###..#..#.####.#..#.####...##.
        #..#.#..#.#..#.#.#..#....#.#..#.......#.
        #..#.#..#.#..#.##...###..##...###.....#.
        ###..####.###..#.#..#....#.#..#.......#.
        #....#..#.#....#.#..#....#.#..#....#..#.
        #....#..#.#....#..#.#....#..#.####..##..
      """.trimIndent(),
      Day10.part2(getResourceAsString("day10.txt"))
    )
  }

}
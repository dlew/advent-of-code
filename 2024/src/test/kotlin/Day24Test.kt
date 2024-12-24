import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day24Test {
  @Test
  fun part1_sample() {
    assertEquals(4, Day24.part1(getResourceAsString("day24-sample.txt")))
    assertEquals(2024, Day24.part1(getResourceAsString("day24-sample2.txt")))
  }

  @Test
  fun part1() {
    assertEquals(43942008931358, Day24.part1(getResourceAsString("day24.txt")))
  }

  @Test
  fun part2() {
    assertEquals("dvb,fhg,fsq,tnc,vcf,z10,z17,z39", Day24.part2(getResourceAsString("day24.txt")))
  }
}
import org.junit.Assert.assertEquals
import org.junit.Test
import utils.getResourceAsString

class Day25Test {

  @Test
  fun part1_sample() {
    assertEquals("2=-1=0", Day25.part1(getResourceAsString("day25-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals("2==0=0===02--210---1", Day25.part1(getResourceAsString("day25.txt")))
  }

}
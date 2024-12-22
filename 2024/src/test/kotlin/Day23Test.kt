import org.junit.jupiter.api.Test
import utils.getResourceAsString
import kotlin.test.assertEquals

class Day23Test {
  @Test
  fun part1_sample() {
    assertEquals(7, Day23.part1(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part1() {
    assertEquals(1314, Day23.part1(getResourceAsString("day23.txt")))
  }

  @Test
  fun part2_sample() {
    assertEquals("co,de,ka,ta", Day23.part2(getResourceAsString("day23-sample.txt")))
  }

  @Test
  fun part2() {
    assertEquals("bg,bu,ce,ga,hw,jw,nf,nt,ox,tj,uu,vk,wp", Day23.part2(getResourceAsString("day23.txt")))
  }
}
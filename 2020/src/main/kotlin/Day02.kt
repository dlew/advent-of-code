import utils.splitNewlines
import utils.toIntList

object Day02 {
  fun part1(input: String) = parse(input)
    .count { p -> p.password.count { it == p.char } in p.start..p.end }

  fun part2(input: String) = parse(input)
    .count { p -> (p.password[p.start - 1] == p.char) != (p.password[p.end - 1] == p.char) }

  private data class PasswordAndPolicy(val start: Int, val end: Int, val char: Char, val password: String)

  private val regex = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")

  private fun parse(input: String) = input.splitNewlines().map { line ->
    val (start, end, char, password) = regex.matchEntire(line)!!.destructured
    return@map PasswordAndPolicy(start.toInt(), end.toInt(), char[0], password)
  }
}
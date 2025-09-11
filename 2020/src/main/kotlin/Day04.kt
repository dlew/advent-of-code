object Day04 {
  fun part1(input: String) = parse(input).count { passport -> isValidSimple(passport) }

  fun part2(input: String) = parse(input).count { passport -> isValid(passport) }

  private val REQUIRED_FIELDS = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

  private fun isValidSimple(passport: Map<String, String>): Boolean {
    return passport.keys.intersect(REQUIRED_FIELDS).size == REQUIRED_FIELDS.size
  }

  private fun isValid(passport: Map<String, String>): Boolean {
    return validateYear(passport["byr"], 1920..2002)
        && validateYear(passport["iyr"], 2010..2020)
        && validateYear(passport["eyr"], 2020..2030)
        && validateHeight(passport["hgt"])
        && validateHairColor(passport["hcl"])
        && validateEyeColor(passport["ecl"])
        && validatePassportId(passport["pid"])
  }

  private fun validateYear(input: String?, range: IntRange): Boolean {
    if (input == null) return false
    val year = input.toIntOrNull() ?: return false
    return year in range
  }

  private val HEIGHT_REGEX = Regex("(\\d+)(cm|in)")

  private fun validateHeight(heightField: String?): Boolean {
    if (heightField == null) return false
    val (height, unit) = HEIGHT_REGEX.matchEntire(heightField)?.destructured ?: return false
    return when (unit) {
      "cm" -> height.toInt() in 150..193
      "in" -> height.toInt() in 59..76
      else -> false
    }
  }

  private val HAIR_COLOR_REGEX = Regex("#[0-9a-f]{6}")

  private fun validateHairColor(hairColorField: String?) = hairColorField?.matches(HAIR_COLOR_REGEX) ?: false

  private val VALID_EYE_COLORS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

  private fun validateEyeColor(eyeColorField: String?) = VALID_EYE_COLORS.contains(eyeColorField)

  private val PID_REGEX = Regex("\\d{9}")

  private fun validatePassportId(passportId: String?) = passportId?.matches(PID_REGEX) ?: false

  private fun parse(input: String): List<Map<String, String>> {
    return input.split("\n\n").map { passport ->
      passport.split(Regex("\\s+"))
        .associate {
          val (key, value) = it.split(":")
          return@associate key to value
        }
    }
  }
}
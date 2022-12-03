object Day02 {

  fun totalScore(input: String): Int {
    return input
      .splitNewlines()
      .map(String::splitWhitespace)
      .sumOf { score(it[0].toPlay(), it[1].toPlay()) }
  }

  fun totalScoreWithStrategy(input: String): Int {
    return input
      .splitNewlines()
      .map(String::splitWhitespace)
      .sumOf { score(it[0].toPlay(), it[1].toOutcome()) }
  }

  private fun score(opponent: Play, outcome: Outcome): Int {
    val you = when (outcome) {
      Outcome.WIN -> opponent.losesAgainst()
      Outcome.LOSE -> opponent.winsAgainst()
      Outcome.DRAW -> opponent
    }

    return score(opponent, you)
  }

  private fun score(opponent: Play, you: Play): Int {
    val outcome = when {
      opponent == you -> Outcome.DRAW
      opponent.losesAgainst() == you -> Outcome.WIN
      else -> Outcome.LOSE
    }

    return you.score + outcome.score
  }

  private enum class Play(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun winsAgainst() = values()[(ordinal + 2) % 3]

    fun losesAgainst() = values()[(ordinal + 1) % 3]
  }

  private enum class Outcome(val score: Int) {
    WIN(6),
    LOSE(0),
    DRAW(3)
  }

  private fun String.toPlay() = when (this) {
    "A" -> Play.ROCK
    "B" -> Play.PAPER
    "C" -> Play.SCISSORS
    "X" -> Play.ROCK
    "Y" -> Play.PAPER
    "Z" -> Play.SCISSORS
    else -> throw IllegalArgumentException("Illegal play: $this")
  }

  private fun String.toOutcome() = when (this) {
    "X" -> Outcome.LOSE
    "Y" -> Outcome.DRAW
    "Z" -> Outcome.WIN
    else -> throw IllegalArgumentException("Illegal play: $this")
  }
}

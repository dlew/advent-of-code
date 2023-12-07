object Day07 {

  private enum class HandType {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD;
  }

  private val cardStrengths = listOf(
    'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
  ).withIndex().associate { it.value to it.index }

  private val cardStrengthsAlt = listOf(
    'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'
  ).withIndex().associate { it.value to it.index }

  private data class HandAndBid(val hand: Hand, val bid: Int)

  private data class Hand(val cards: String) {
    val handType = calculateHandType(cards)
    val handTypeAlt = calculateHandTypeWithJokers(cards)
  }

  private fun generateHandSelectors(useAlt: Boolean): Array<(Hand) -> Comparable<*>?> {
    val typeSelector = if (useAlt) { hand: Hand -> hand.handTypeAlt } else { hand: Hand -> hand.handType }
    val cardStrengths = if (useAlt) cardStrengthsAlt else cardStrengths
    val cardSelectors = (0..4).map<Int, (Hand) -> Int?> { index ->
      { hand -> cardStrengths[hand.cards[index]] }
    }
    return (listOf(typeSelector) + cardSelectors).toTypedArray()
  }

  private val cardSelectors = generateHandSelectors(false)
  private val cardSelectorsAlt = generateHandSelectors(true)

  private val handComparator = Comparator<HandAndBid> { h1, h2 -> compareValuesBy(h1.hand, h2.hand, *cardSelectors) }
  private val handComparatorAlt =
    Comparator<HandAndBid> { h1, h2 -> compareValuesBy(h1.hand, h2.hand, *cardSelectorsAlt) }

  fun part1(input: String) = solve(input, handComparator)

  fun part2(input: String) = solve(input, handComparatorAlt)

  private fun solve(input: String, comparator: Comparator<HandAndBid>): Int {
    return parseInput(input)
      .sortedWith(comparator)
      .asReversed()
      .map { it.bid }
      .withIndex()
      .sumOf { (index, bid) -> (index + 1) * bid }
  }

  private fun calculateHandTypeWithJokers(cards: String): HandType {
    return if (cards.none { it == 'J' }) {
      calculateHandType(cards)
    } else {
      cards
        .toCharArray()
        .distinct()
        .map { type -> calculateHandType(cards.replace('J', type)) }
        .minOf { it }
    }
  }

  private fun calculateHandType(cards: String): HandType {
    val cardCounts = cards.groupBy { it }.mapValues { it.value.size }

    if (cardCounts.containsValue(5)) {
      return HandType.FIVE_OF_A_KIND
    }

    if (cardCounts.containsValue(4)) {
      return HandType.FOUR_OF_A_KIND
    }

    if (cardCounts.containsValue(3)) {
      return if (cardCounts.containsValue(2)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
    }

    return when (cardCounts.count { it.value == 2 }) {
      2 -> HandType.TWO_PAIR
      1 -> HandType.ONE_PAIR
      else -> HandType.HIGH_CARD
    }
  }

  private fun parseInput(input: String): List<HandAndBid> {
    return input.splitNewlines().map {
      val (cards, bid) = it.splitWhitespace()
      return@map HandAndBid(Hand(cards), bid.toInt())
    }
  }
}
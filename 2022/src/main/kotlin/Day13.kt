import Day13.Data.DataList
import Day13.Data.DataValue
import kotlin.math.min

object Day13 {

  fun part1(input: String): Int {
    return input.split("\n\n")
      .map { it.splitNewlines() }
      .map { (left, right) -> compare(parse(left), parse(right)) }
      .foldIndexed(0) { index, acc, result -> acc + if (result < 0) index + 1 else 0 }
  }

  fun part2(input: String): Int {
    val dividers = listOf(parse("[[2]]"), parse("[[6]]"))

    val data = input
      .splitNewlines()
      .filter { it.isNotEmpty() }
      .map { parse(it) }
      .plus(dividers)
      .sortedWith(::compare)

    return dividers.map { data.indexOf(it) + 1 }.reduce(Int::times)
  }

  private fun compare(left: Data, right: Data): Int {
    return when (left) {
      is DataValue -> when (right) {
        is DataList -> compareLists(DataList(listOf(left)), right)
        is DataValue -> left.value - right.value
      }

      is DataList -> when (right) {
        is DataList -> compareLists(left, right)
        is DataValue -> compareLists(left, DataList(listOf(right)))
      }
    }
  }

  private fun compareLists(left: DataList, right: DataList): Int {
    for (index in 0 until min(left.list.size, right.list.size)) {
      val result = compare(left.list[index], right.list[index])
      if (result != 0) {
        return result
      }
    }

    return left.list.size - right.list.size
  }

  private fun parse(packet: String) = parse(packet, 1).first

  private fun parse(data: String, start: Int): Pair<Data, Int> {
    val list = mutableListOf<Data>()
    val curr = StringBuilder()

    fun appendValueIfPresent() {
      if (curr.isNotEmpty()) {
        list.add(DataValue(curr.toString().toInt()))
        curr.clear()
      }
    }

    var index = start
    while (index < data.length) {
      when (val char = data[index]) {
        '[' -> {
          val (innerData, newIndex) = parse(data, index + 1)
          list.add(innerData)
          index = newIndex
        }

        ']' -> {
          appendValueIfPresent()
          return DataList(list) to index + 1
        }

        ',' -> {
          appendValueIfPresent()
          index++
        }

        else -> {
          curr.append(char)
          index++
        }
      }
    }

    throw IllegalStateException("Unexpected parser failure!")
  }

  private sealed class Data {
    data class DataList(val list: List<Data>) : Data()
    data class DataValue(val value: Int) : Data()
  }

}
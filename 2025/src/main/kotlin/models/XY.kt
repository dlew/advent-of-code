package models

data class XY(
  val x: Int,
  val y: Int,
) : Comparable<XY> {
  override fun compareTo(other: XY) = compareValuesBy(this, other, { it.x }, { it.y })
}

fun Array<CharArray>.getXY(xy: XY): Char? {
  if (xy.y !in this.indices || xy.x !in this[0].indices) return null
  return this[xy.y][xy.x]
}

fun Array<IntArray>.getXY(xy: XY): Int? {
  if (xy.y !in this.indices || xy.x !in this[0].indices) return null
  return this[xy.y][xy.x]
}

fun <T> List<List<T>>.getXY(xy: XY): T? {
  if (xy.y !in this.indices || xy.x !in this[0].indices) return null
  return this[xy.y][xy.x]
}

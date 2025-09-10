package models

data class XY(
  val x: Int,
  val y: Int,
) : Comparable<XY> {
  override fun compareTo(other: XY) = compareValuesBy(this, other, { it.x }, { it.y })
}

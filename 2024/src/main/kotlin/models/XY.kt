package models

import models.Direction.E
import models.Direction.N
import models.Direction.NE
import models.Direction.NW
import models.Direction.S
import models.Direction.SE
import models.Direction.SW
import models.Direction.W

data class XY(
  val x: Int,
  val y: Int,
) : Comparable<XY> {
  fun move(direction: Direction) = when (direction) {
    N -> copy(y = y - 1)
    NE -> copy(x = x + 1, y = y - 1)
    E -> copy(x = x + 1)
    SE -> copy(x = x + 1, y = y + 1)
    S -> copy(y = y + 1)
    SW -> copy(x = x - 1, y = y + 1)
    W -> copy(x = x - 1)
    NW -> copy(x = x - 1, y = y - 1)
  }

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

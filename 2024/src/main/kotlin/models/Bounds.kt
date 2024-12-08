package models

data class Bounds(val xRange: IntRange, val yRange: IntRange)

fun XY.inBounds(bounds: Bounds) = x in bounds.xRange && y in bounds.yRange
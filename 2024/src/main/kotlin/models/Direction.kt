package models

enum class Direction {
  N,
  NE,
  E,
  SE,
  S,
  SW,
  W,
  NW
}

val CARDINAL_DIRECTIONS = listOf(Direction.N, Direction.E, Direction.S, Direction.W)
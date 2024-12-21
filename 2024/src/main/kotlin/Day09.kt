object Day09 {
  // Optimized (if a bit ugly) solution: computes each block's value inline (no disk manipulation needed)
  fun part1(input: String): Long {
    val disk = parse(input)
    var total = 0L
    var left = 0
    var right = disk.size - 1
    while(left <= right) {
      val block = disk[left]
      if (block != null) {
        total += block * left
      }
      else {
        var rightBlock = disk[right]
        while (rightBlock == null) {
          rightBlock = disk[--right]
        }
        total += rightBlock * left
        right--
      }
      left++
    }
    return total
  }

  fun part2(input: String) = parse(input).compactByFile().checksum()

  private fun Array<Int?>.compactByFile(): List<Int?> {
    val disk = this
    var left = 0
    var right = disk.size - 1
    while (left < right) {
      if (disk[left] != null) {
        left++
      } else if (disk[right] == null) {
        right--
      } else {
        // Determine file size
        val value = disk[right]
        var fileStart = right
        while(disk[fileStart - 1] == value) {
          fileStart--
        }
        val fileSize = right - fileStart + 1

        // Find a blank space with space for it
        var pos = left
        var numFree = 0
        while(pos < fileStart) {
          if (disk[pos] == null) {
            numFree++

            if (numFree == fileSize) {
              disk.fill(value, pos - numFree + 1, pos + 1)
              disk.fill(null, fileStart, fileStart + fileSize)
              right = fileStart - 1
              break
            }
          }
          else {
            numFree = 0
          }

          pos++
        }

        // If no blank space found, move to the next file
        if (pos == fileStart) {
          right = fileStart - 1
        }
      }
    }
    return disk.toList()
  }

  private fun List<Int?>.checksum(): Long {
    return map { it?.toLong() ?: 0L }
      .withIndex()
      .sumOf { (index, value) -> index * value }
  }

  private fun parse(input: String): Array<Int?> {
    var id = 0
    return input.flatMapIndexed { index, c ->
      val size = c.digitToInt()
      if (index % 2 == 0) List<Int?>(size) { id }.also { id++ }
      else List<Int?>(size) { null }
    }.toTypedArray()
  }
}
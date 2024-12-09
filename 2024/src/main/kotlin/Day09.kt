object Day09 {
  fun part1(input: String) = parse(input).compactByBlock().checksum()

  fun part2(input: String) = parse(input).compactByFile().checksum()

  private fun List<Int?>.compactByBlock(): List<Int?> {
    val disk = toMutableList()
    var left = 0
    var right = disk.size - 1
    while (left < right) {
      if (disk[left] != null) {
        left++
      } else if (disk[right] == null) {
        right--
      } else {
        disk[left] = disk[right]
        disk[right] = null
      }
    }
    return disk
  }

  private fun List<Int?>.compactByFile(): List<Int?> {
    val disk = toTypedArray()
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

  private fun parse(input: String): List<Int?> {
    var id = 0
    return input.flatMapIndexed { index, c ->
      val size = c.digitToInt()
      if (index % 2 == 0) List<Int?>(size) { id }.also { id++ }
      else List<Int?>(size) { null }
    }
  }
}
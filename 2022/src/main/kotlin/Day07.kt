object Day07 {

  fun part1(input: String): Int {
    val root = parseCommandHistory(input)
    val sizes = getDirectorySizes(root)
    return sizes.values.filter { it <= 1e5 }.sum()
  }

  fun part2(input: String): Int {
    val root = parseCommandHistory(input)
    val sizes = getDirectorySizes(root)
    val neededSpace = sizes[root]!! - 4e7
    return sizes
      .values
      .sorted()
      .first { it >= neededSpace }
  }

  private fun getDirectorySizes(dir: Entry.Directory): Map<Entry.Directory, Int> {
    val sizes = mutableMapOf<Entry.Directory, Int>()
    var currSize = 0
    dir.entries.forEach { entry ->
      when (entry) {
        is Entry.File -> currSize += entry.size
        is Entry.Directory -> {
          sizes.putAll(getDirectorySizes(entry))
          currSize += sizes[entry]!!
        }
      }
    }
    sizes[dir] = currSize
    return sizes
  }

  private fun parseCommandHistory(history: String): Entry.Directory {
    val root = Entry.Directory("/", null)
    var curr = root

    history
      .splitNewlines()
      .forEach { cmd ->
        when {
          cmd == "$ cd /" -> curr = root

          cmd == "$ cd .." -> curr = curr.parent!!

          cmd.startsWith("$ cd") -> curr = curr.entries
            .filterIsInstance<Entry.Directory>()
            .find { it.name == cmd.drop(5) }!!

          cmd == "$ ls" -> {}

          cmd.startsWith("dir") -> curr.entries.add(Entry.Directory(cmd.drop(4), parent = curr))

          else -> curr.entries.add(Entry.File(cmd.splitWhitespace()[0].toInt()))
        }
      }

    return root
  }

  sealed class Entry {
    data class File(val size: Int) : Entry()

    data class Directory(val name: String, val parent: Directory?) : Entry() {
      val entries: MutableList<Entry> = mutableListOf()
    }
  }

}
import models.Bounds
import models.XY
import models.inBounds
import java.awt.Canvas
import java.awt.Color
import java.awt.Frame
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import java.io.File
import java.lang.Thread.sleep
import javax.imageio.ImageIO
import kotlin.math.abs


object Day14 {
  fun part1(input: String, width: Int, height: Int): Int {
    val robots = parse(input)
      .map { it.move(width, height, 100) }

    val lowerX = 0..<width / 2
    val lowerY = 0..<height / 2
    val upperX = width / 2 + 1..<width
    val upperY = height / 2 + 1..<height

    val quadrants = listOf(
      Bounds(lowerX, lowerY),
      Bounds(upperX, lowerY),
      Bounds(lowerX, upperY),
      Bounds(upperX, upperY),
    )

    return quadrants.map { quadrant ->
      robots.count { robot ->
        robot.position.inBounds(quadrant)
      }
    }.reduce(Int::times)
  }

  fun part2(input: String) {
    val width = 101
    val height = 103
    val scale = 8

    // Setup a place to draw
    val canvas = BathroomCanvas(width, height)
    val frame = Frame()
    frame.add(canvas)
    frame.isVisible = true
    frame.setSize(width * scale, frame.insets.top + height * scale)
    val bufferedImage = BufferedImage(width * scale, height * scale, TYPE_INT_ARGB)
    sleep(500) // So the Frame can "boot up"

    // Create somewhere to store the images
    val outDir = File("out")
    if (!outDir.exists()) {
      outDir.mkdir()
    }

    // From initial tests, the pattern starts appearing at time 98 and clarifies itself every 101 seconds
    val start = 98
    val stop = 7774
    val step = 101
    canvas.robots = parse(input).map { it.move(width, height, start) }
    (start..stop step step).forEach { day ->
      // Output (to frame & file)
      frame.title = "Time=${day}"
      canvas.repaint()
      canvas.paint(bufferedImage.graphics)
      ImageIO.write(bufferedImage, "PNG", File("out/day-$day.png"))

      // Move robots
      canvas.robots = canvas.robots.map { it.move(width, height, step) }
    }
  }

  private data class Robot(val num: Int, val position: XY, val velocity: XY)

  private fun Robot.move(width: Int, height: Int, times: Int): Robot {
    val x = position.x + (velocity.x * times)
    val y = position.y + (velocity.y * times)
    return copy(
      position = XY(
        x = if (x >= 0) x % width else (width - abs(x) % width) % width,
        y = if (y >= 0) y % height else (height - abs(y) % height) % height
      )
    )
  }

  private class BathroomCanvas(val gridWidth: Int, val gridHeight: Int) : Canvas() {
    var robots: List<Robot> = emptyList()

    override fun paint(g: Graphics) {
      super.paint(g)
      val robotWidth = width / gridWidth
      val robotHeight = height / gridHeight
      g.color = Color.WHITE
      g.fillRect(0, 0, width, height)
      g.color = Color.BLACK
      robots.forEach { robot ->
        g.fillRect(robot.position.x * robotWidth, robot.position.y * robotHeight, robotWidth, robotHeight)
      }
    }
  }

  private fun parse(input: String): List<Robot> {
    val regex = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)".toRegex()
    var num = 0
    return input.splitNewlines().map { line ->
      val (px, py, vx, vy) = regex.matchEntire(line)!!.groupValues.drop(1).toIntList()
      return@map Robot(num++, XY(px, py), XY(vx, vy))
    }
  }
}
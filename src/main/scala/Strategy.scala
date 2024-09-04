package triangle.paths

import cats.effect.IO
import fs2.Stream

sealed trait Strategy
object Strategy {

  case object DFS extends Strategy {

    // Depth-First Search function to find the path with the smallest sum
    def traverse(triangle: Triangle, f: Function): Vector[Int] = {
      def dfs(row: Int, col: Int, currentPath: Vector[Int], currentSum: Int): (Vector[Int], Int) = {
        if (row == triangle.length) {
          // When we reach the bottom of the triangle, we return the current path and sum.
          (currentPath, currentSum)
        } else {
          val value = triangle(row)(col)
          val newPath = currentPath :+ value
          val newSum = currentSum + value

          if (col == triangle(row).length - 1) {
            dfs(row + 1, col, newPath, newSum)
          } else {
            val leftPath = dfs(row + 1, col, newPath, newSum)
            val rightPath = dfs(row + 1, col + 1, newPath, newSum)

            if (f(leftPath._2, rightPath._2)) leftPath else rightPath
          }
        }
      }

      dfs(0, 0, Vector.empty, 0)._1
    }
  }

  case object Optimized extends Strategy {

    private case class PathInfo(sum: Int, path: Vector[Int])

    def processStream(stream: Stream[IO, Vector[Int]]) =
      stream.compile.fold(Vector.empty[PathInfo]) { case (acc, row) =>
          if (acc.isEmpty) row.map(value => PathInfo(value, Vector(value)))
          else row.zipWithIndex.map { case (value, i) =>
            val left = if (i > 0) acc(i - 1) else PathInfo(Int.MaxValue, Vector.empty)
            val right = if (i < acc.length) acc(i) else PathInfo(Int.MaxValue, Vector.empty)

            if (left.sum <= right.sum) PathInfo(left.sum + value, left.path.appended(value))
            else PathInfo(right.sum + value, right.path.appended(value))
          }
        }
      .map(_.minBy(_.path.sum).path)
  }

  def fromString(s: String): Option[Strategy] = s.toLowerCase match {
    case "dfs" => Some(DFS)
    case "optimized" => Some(Optimized)
    case _ => None
  }
  
}

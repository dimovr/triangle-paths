package triangle.paths

sealed trait Strategy
object Strategy {

  case object DFS extends Strategy {

    // Depth-First Search function to find the path with the smallest sum
    def traverse(triangle: Triangle, f: Function): (Vector[Int], Int) = {
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

            if (f(leftPath._2,  rightPath._2)) leftPath else rightPath
          }
        }
      }

      dfs(0, 0, Vector.empty, 0)
    }
  }
  case object Optimized extends Strategy

  def fromString(s: String): Option[Strategy] = s.toLowerCase match {
    case "dfs" => Some(DFS)
    case "optimized" => Some(Optimized)
    case _ => None
  }
}

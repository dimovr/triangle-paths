package triangle.paths

import cats.Functor
import cats.effect.Concurrent
import fs2.Pipe

trait PathFinder[F[_]] {
  def findPath(function: Function): Pipe[F, Vector[Int], Vector[Int]]
}
object PathFinder {

  private case class PathInfo(sum: Int, path: Vector[Int])

  def apply[F[_]: Concurrent: Functor] = new PathFinder[F] {
    override def findPath(function: Function): Pipe[F, Vector[Int], Vector[Int]] =
      _.fold(Vector.empty[PathInfo]) { case (acc, row) =>
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

}

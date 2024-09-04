package triangle.paths

sealed trait Strategy
object Strategy {
  case object DFS extends Strategy
  case object Optimized extends Strategy
}

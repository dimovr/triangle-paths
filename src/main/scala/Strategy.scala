package triangle.paths

sealed trait Strategy
object Strategy {
  case object DFS extends Strategy
  case object Optimized extends Strategy
  
  def fromString(s: String): Option[Strategy] = s.toLowerCase match {
    case "dfs" => Some(DFS)
    case "optimized" => Some(Optimized)
    case _ => None
  }
}

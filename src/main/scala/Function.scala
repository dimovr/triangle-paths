package triangle.paths

sealed trait Function

object Function {
  case object Min extends Function

  case object Max extends Function

  def fromString(s: String): Option[Function] = s.toLowerCase match {
    case "min" => Some(Min)
    case "max" => Some(Max)
    case _ => None
  }
}
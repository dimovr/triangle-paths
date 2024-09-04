package triangle.paths

sealed trait Function {
  val comparator: (Int, Int) => Boolean
}

object Function {
  case object Min extends Function {
    override val comparator: (Int, Int) => Boolean = _ < _
  }

  case object Max extends Function {
    override val comparator: (Int, Int) => Boolean = _ > _
  }

  def fromString(s: String): Option[Function] = s.toLowerCase match {
    case "min" => Some(Min)
    case "max" => Some(Max)
    case _ => None
  }
}
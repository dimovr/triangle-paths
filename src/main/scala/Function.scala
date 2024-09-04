package triangle.paths

sealed trait Function
object Function {
  case object Min extends Function
  case object Max extends Function
}

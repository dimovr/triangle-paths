package triangle.paths

import cats.effect.{ExitCode, IO, IOApp}

object TrianglePath extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    // todo: use decline for prefixed arguments instead of parsing positionally
    args match {
      case triangleFileInput :: strategy :: function :: Nil =>
        IO.println(s"Calculating $function using $strategy on data from $triangleFileInput").as(ExitCode.Success)
      case _ => IO.println("Invalid input.").as(ExitCode.Error)
    }

}

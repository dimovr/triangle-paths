package triangle.paths

import cats.effect.{IO, IOApp}

object MinTrianglePath extends IOApp.Simple {

  override def run: IO[Unit] =
    IO.println("Hello! I'm about to triangulate")

}

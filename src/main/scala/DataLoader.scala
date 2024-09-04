package triangle.paths

import cats.effect.Resource
import cats.effect.IO
import fs2.{Stream, text}
import fs2.io.file.{Files, Path}

object DataLoader {

  def dataStream(filePath: Path): Stream[IO, Vector[Int]] =
    Files[IO].readAll(filePath)
      .through(text.utf8.decode)
      .through(text.lines)
      .filter(_.nonEmpty)
      .map(_.split(" ").map(_.toInt).toVector)

  // Function to load the file and process it
  def dataResource(filePath: Path)(process: Triangle => Vector[Int]) =
    Resource.make(IO(dataStream(filePath)))(_ => IO.unit)

}

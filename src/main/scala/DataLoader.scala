package triangle.paths

import cats.effect.Resource
import cats.effect.IO
import fs2.{Stream, text}
import fs2.io.file.{Files, Path}

object DataLoader {

  // Function to parse a line of text into a Vector[Int]
  def parseLine(line: String): Vector[Int] = line.split(" ").map(_.toInt).toVector

  // Function to load the file and process it
  def loadFile(filePath: Path)(process: Triangle => IO[String]): IO[String] = {
    val fileResource: Resource[IO, Stream[IO, String]] =
      Resource.make(
        IO(Files[IO].readAll(filePath).through(text.utf8.decode).through(text.lines))
      )(_ => IO.unit)

    fileResource.use { stream =>
      stream
        .map(parseLine)
        .compile
        .to(Vector)
        .flatMap(process)
    }
  }

}

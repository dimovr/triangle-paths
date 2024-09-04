package triangle.paths

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.*
import fs2.io.file.Path
import com.monovore.decline.*

object TrianglePath extends IOApp {

  private val pathOpt: Opts[Path] =
    Opts.option[String]("i", help = "Input file path").map(Path(_))

  private val functionOpt: Opts[Function] =
    Opts.option[String]("f", help = "Function (Min, Max)")
      .mapValidated(f => Function.fromString(f).toValidNel(s"Invalid function: $f"))

  private val command = Command("triangle-path", "")((pathOpt, functionOpt).tupled)

  // Function to read and process the triangle from the file
  private def processFile(filePath: Path, function: Function): IO[String] =
    DataLoader
      .dataStream(filePath)
      .through(PathFinder[IO].findPath(function))
      .compile
      .lastOrError
      .map(path => s"$function path is: ${path.mkString(" + ")} = ${path.sum}")

  override def run(args: List[String]): IO[ExitCode] =
    command.parse(args) match {
      case Left(help) => IO.pure(Console.err.println(help)).as(ExitCode.Error)
      case Right((path, function)) =>
        processFile(path, function).flatMap(IO.println).as(ExitCode.Success)
    }

}

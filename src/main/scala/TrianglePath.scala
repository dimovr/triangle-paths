package triangle.paths

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.io.file.Path
import com.monovore.decline._

object TrianglePath extends IOApp {

  private val pathOpt: Opts[Path] =
    Opts.option[String]("i", help = "Input file path").map(Path(_))

  private val strategyOpt: Opts[Strategy] =
    Opts.option[String]("s", help = "Processing strategy (DFS, Optimized)")
      .mapValidated(s => Strategy.fromString(s).toValidNel(s"Invalid strategy: $s"))

  private val functionOpt: Opts[Function] =
    Opts.option[String]("f", help = "Function (Min, Max)")
      .mapValidated(f => Function.fromString(f).toValidNel(s"Invalid function: $f"))

  private val command =
    Command("triangle-path", "")((pathOpt, strategyOpt, functionOpt).tupled)

  // Function to read and process the triangle from the file
  private def  processFile(path: Path, function: Function): IO[String] =
    DataLoader.loadFile(path) { triangle =>
      val (path, sum) = Strategy.DFS.traverse(triangle, function)
      IO.pure(s"Minimal path is: ${path.mkString(" + ")} = $sum")
    }

  override def run(args: List[String]): IO[ExitCode] =
    command.parse(args) match {
      case Left(help) => IO.pure(Console.err.println(help)).as(ExitCode.Error)
      case Right((path, strategy, function)) =>
        processFile(path, function).flatMap(IO.println).as(ExitCode.Success)
    }
}

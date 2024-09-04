package triangle.paths

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.*
import fs2.io.file.Path
import com.monovore.decline.*
import triangle.paths.Strategy.DFS

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
  private def processFile(filePath: Path, strategy: Strategy, function: Function): IO[String] = {
    val resultPath = strategy match {
      case Strategy.Optimized => Strategy.Optimized.processStream(DataLoader.dataStream(filePath))
      case Strategy.DFS => DataLoader.withDataResource(filePath)(Strategy.DFS.traverse(_, function))
    }

    resultPath.map(path => s"Minimal path is: ${path.mkString(" + ")} = ${path.sum}")
  }

  override def run(args: List[String]): IO[ExitCode] =
    command.parse(args) match {
      case Left(help) => IO.pure(Console.err.println(help)).as(ExitCode.Error)
      case Right((path, strategy, function)) =>
        processFile(path, strategy, function).flatMap(IO.println).as(ExitCode.Success)
    }

}

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

  override def  run(args: List[String]): IO[ExitCode] =
    command.parse(args) match {
      case Left(help) => IO.pure(Console.err.println(help)).as(ExitCode.Error)
      case Right((path, strategy, function)) =>
        IO.println(s"Calculating $function using $strategy on data from ${path.toString}").as(ExitCode.Success)
    }

}

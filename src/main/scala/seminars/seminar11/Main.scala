package seminars.seminar11

import io.finch._
import cats.effect.{ExitCode, IO, IOApp}
import seminars.seminar11.elections.{ElectionsConfig, ElectionsModule, ElectionsServiceImpl}

object Main extends IOApp with Endpoint.Module[IO] {

  val cfg =
    AppConfig(
      elections = ElectionsConfig(List("a", "b", "c", "d"))
    )

  def run(args: List[String]): IO[ExitCode] =
    for {
      services <- Services(cfg)
      modules = Modules(services)
      _ <- Server.run(modules)
    } yield ExitCode.Success
}
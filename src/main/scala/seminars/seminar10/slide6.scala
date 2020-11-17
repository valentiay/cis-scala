package seminars.seminar10

import java.nio.file.Paths
import java.util.concurrent.Executors

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import fs2.io.file

import scala.concurrent.ExecutionContext

object slide6 extends IOApp {
  val path = Paths.get("src/main/scala/seminars/seminar10/slide6.scala")

  val blockingExecutor = Executors.newFixedThreadPool(4)
  val blockingContext = ExecutionContext.fromExecutor(blockingExecutor)
  val blocker = Blocker.liftExecutionContext(blockingContext)

  def run(args: List[String]): IO[ExitCode] =
    file
      .readAll[IO](path, blocker, 256)
      .split(_ == '\n')
      .zipWithIndex
      .map { case (chunk, idx) => s"$idx\t${chunk.foldLeft("")((str, byte) => str + byte.toChar)}" }
      .evalTap(line => IO(println(line)))
      .compile
      .drain
      .as(ExitCode.Success)
      .guarantee(IO(blockingExecutor.shutdown()))
}

package seminars.seminar10

import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream

import scala.concurrent.duration._

object slide4 extends IOApp {
  val pipe1: Stream[IO, Int] => Stream[IO, Unit] =
    stream =>
      stream.evalMap(int => IO(println(s"Pipe1: $int")))

  val pipe2: Stream[IO, Int] => Stream[IO, Unit] =
    stream =>
      stream.evalMap(int => IO(println(s"Pipe2: $int")))

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- Stream
        .awakeEvery[IO](1.seconds)
        .zipRight(Stream.random[IO])
        .broadcastTo(pipe1, pipe2)
        .compile
        .drain
    } yield ExitCode.Success
}

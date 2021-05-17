package seminars.seminar10

import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream

import scala.concurrent.duration._

object slide4 extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val pipe1: Stream[IO, Int] => Stream[IO, Unit] =
      stream =>
        stream.evalMap(int => IO(println(s"Pipe1: $int")))

    val pipe2: Stream[IO, Int] => Stream[IO, Unit] =
      stream =>
        Stream.awakeEvery[IO](10.second) .zipRight(stream.evalMap(int => IO(println(s"Pipe2: $int"))))

    for {
      _ <- Stream
        .awakeEvery[IO](1.seconds)
        .zipRight(Stream.random[IO])
        .broadcastTo(pipe2, pipe1)
        .compile
        .drain
    } yield ExitCode.Success
  }
}

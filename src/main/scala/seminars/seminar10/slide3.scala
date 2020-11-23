package seminars.seminar10

import fs2.Stream
import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration._
import scala.util.Random

object slide3 extends IOApp {
  val stream =
    Stream(1, 2, 3, 4) ++
      Stream.raiseError[IO](new NoSuchElementException("5")) ++
      Stream(6, 7, 8)

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("\nunhandled"))
      unhandled <- stream
        .evalTap(int => IO(println(int)))
        .compile
        .toList
        .attempt
      _ <- IO(println(unhandled))

      _ <- IO(println("\nattempt"))
      attempt <- stream
        .attempt
        .evalTap(int => IO(println(int)))
        .compile
        .toList
      _ <- IO(println(attempt))


      _ <- IO(println("\nhandle"))
      handle <- stream
        .handleErrorWith(throwable => Stream.eval(IO(throwable.getMessage.toInt)))
        .evalTap(int => IO(println(int)))
        .compile
        .toList
      _ <- IO(println(handle))

      _ <- IO(println("\nattempts"))
      attempts <- Stream.eval(IO(Random.nextInt(2)))
        .map(1 / _)
        .attempts(Stream.constant(1.second))
        .evalTap(int => IO(println(int)))
        .collect { case Right(a) => a }
        .take(10)
        .compile
        .toList
      _ <- IO(println(attempts))
    } yield ExitCode.Success
}

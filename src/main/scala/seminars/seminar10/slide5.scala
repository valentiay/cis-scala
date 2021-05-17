package seminars.seminar10

import cats.effect.{ExitCode, IO, IOApp}
import fs2.concurrent.Queue
import fs2.Stream
import scala.util.Random
import scala.concurrent.duration._

object slide5 extends IOApp {

  def enqueue(queue: Queue[IO, Int]): IO[Unit] =
    for {
      _ <- IO(println("inserting"))
      _ <- queue.enqueue1(Random.nextInt)
      _ <- enqueue(queue)
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    for {
      queue <- Queue.bounded[IO, Int](10)
      fiber <- Stream.awakeEvery[IO](10.seconds).zipRight( queue.dequeue.evalTap(int => IO(println(int)))).compile.drain.start
      _ <- enqueue(queue)
      _ <- fiber.join
    } yield ExitCode.Success
}

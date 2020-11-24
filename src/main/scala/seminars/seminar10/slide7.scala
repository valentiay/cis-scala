package seminars.seminar10

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, ExitCode, IO, IOApp}
import fs2.Stream

import scala.util.Random

object slide7 extends IOApp {

  implicit class StreamOps[F[_], G[_], A](val compiled: Stream.CompileOps[F, G, A]) extends AnyVal {
    def groupMapReduce[K, B](key: A => K)(f: A => B)(reduce: (B, B) => B): G[Map[K, B]] =
      compiled.fold(Map.empty[K, B]) {
        (map, a) =>
          map.updatedWith(key(a)) {
            case Some(b) => Some(reduce(b, f(a)))
            case None => Some(f(a))
          }
      }
  }

  def run(args: List[String]): IO[ExitCode] =
    for {
      start <- Clock[IO].monotonic(TimeUnit.SECONDS)
      stats <- Stream
        .repeatEval(IO(Random.alphanumeric.take(3).mkString))
        .take(10000000L)
        .compile
        .groupMapReduce(_.toLowerCase)(_ => 1)(_ + _)
      end <- Clock[IO].monotonic(TimeUnit.SECONDS)
      _ <- IO(println(stats.mkString("\n")))
      _ <- IO(println(s"Computation took ${end - start} s"))
    } yield ExitCode.Success

}

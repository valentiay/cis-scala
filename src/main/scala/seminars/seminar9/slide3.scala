package seminars.seminar9

import java.util.concurrent.TimeUnit

import cats.effect.concurrent.{MVar, MVar2}
import cats.effect.{Clock, ExitCode, IO, IOApp}
import cats.implicits._

import scala.util.Random
import scala.concurrent.duration._

// MVar - примитив синхронизации, представляющий собой ячейку, возможно пустую.
// Также его можно считать конкурентной очередью из одного элемента
// Основные методы:
//   .take: F[A] - взять элемент из ячейки. Если ячейка пуста, ждет, пока она заполнится
//   .put(a: A): F[Unit] - положить элемент в ячейку. Если ячейка заполнена, ждет, пока она станет пустой
//   .read: F[A] - прочитать элемент из ячейки, если она непуста. Если ячейка пуста, ждет, пока она заполнится
object slide3 extends IOApp {

  val cacheTtlMs = 10000L

  val heavyMethod: IO[Int] =
    IO(println("Computing value")) *>
      IO(Random.nextInt(10000)).flatMap(int => IO.sleep(int.millis).as(int))

  def tryUpdateCache[T](mvar: MVar2[IO, (T, Long)], compute: IO[T]): IO[T] =
    for {
      _ <- IO(println("Trying to update cache"))
      cache <- mvar.take
      now <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      newCache <- if (cache._2 > now) IO.pure(cache) else compute.map(_ -> (now + cacheTtlMs))
      _ <- mvar.put(newCache)
    } yield newCache._1

  def readCache[T](mvar: MVar2[IO, (T, Long)], compute: IO[T]): IO[T] =
    for {
      now <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      value <- mvar.read.flatMap {
        case (value, timestamp) if timestamp > now => IO.pure(value)
        case _ => tryUpdateCache(mvar, compute)
      }
    } yield value

  def readCacheTwice(mvar: MVar2[IO, (Int, Long)]): IO[Unit] =
    List.fill(2)(readCache(mvar, heavyMethod).flatTap(x => IO(println(x)))).parSequence.void

  def run(args: List[String]): IO[ExitCode] =
    for {
      mvar <- MVar.of[IO, (Int, Long)]((0, 0L))
      _ <- (readCacheTwice(mvar) *> IO.sleep(2.seconds)).foreverM
    } yield ExitCode.Success
}

package seminars.seminar9

import cats.effect.concurrent.Deferred
import cats.effect.{ExitCase, ExitCode, IO, IOApp}
import cats.implicits._

import scala.concurrent.duration._
import scala.util.Random

// Deferred - примитив синхронизации, который предсталяет собой ячейку, которую можно заполнить лишь один раз.
// Значение записывается методом .complete, читается методом .get.
// При первом вызове .complete отрабатывает успешной, последующие попытки возвращают ошибку.
// Метод .get асинхронно ожидает заполнения ячейки, затем возвращает значение
// В каком-то роде является функциональным аналогом scala.concurrent.Promise
object slide1 extends IOApp {

  def race[T](deferred: Deferred[IO, T])(tasks: List[IO[T]]): IO[T] =
    for {
      fibers <- tasks.parTraverse(_.flatMap(deferred.complete).handleErrorWith(error => IO(println(error))).start)
      res <- deferred.get
      _ <- fibers.traverse(_.cancel)
    } yield res

  def createTask(duration: FiniteDuration): IO[FiniteDuration] =
    // Метод guaranteeCase обрабатывает результат завершения эффекта: успех, ошибку или отмену
    IO.sleep(duration).guaranteeCase {
      case ExitCase.Completed => IO(println(s"Completed sleep $duration"))
      case ExitCase.Error(e) => IO(println(s"Failed sleep $duration, $e"))
      case ExitCase.Canceled => IO(println(s"Cancelled $duration"))
    }.as(duration)

  def run(args: List[String]): IO[ExitCode] =
    for {
      deferred <- Deferred.apply[IO, FiniteDuration]
      durations <- IO(List.fill(10)(Random.nextInt(10000).millis))
      _ <- IO(println(durations.sorted.mkString("Options:\n", "\n", "\n")))
      tasks = (durations.min :: durations).map(createTask)
      result <- race(deferred)(tasks)
      _ <- IO(println(s"\nResult: $result\n"))
    } yield ExitCode.Success
}

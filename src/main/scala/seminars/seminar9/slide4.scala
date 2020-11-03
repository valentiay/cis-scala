package seminars.seminar9

import cats.effect.concurrent.Semaphore
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

import scala.concurrent.duration._
import scala.util.Random

// Semaphore - примитив, позволяющий ограничить число потоков, которые одновременно находятся в критической секции
// Основные методы:
//   .acquire: F[Unit] - получить "жетон". Если доступных жетонов нет, метод ждет, пока они появятся.
//   .release: F[Unit] - вернуть "жетон", чтобы позволить новым потокам войти в критическую секцию.
//   .withPermit(fa: F[A]): F[A] - оборачивает fa в acquire и release.
object slide4 extends IOApp {
  def heavyMethod(semaphore: Semaphore[IO])(duration: FiniteDuration): IO[Unit] =
    semaphore.acquire *>
      IO(println(duration)) *>
      IO.sleep(duration) *>
      semaphore.release

  def run(args: List[String]): IO[ExitCode] =
    for {
      durations <- IO(List.fill(1000)(Random.nextInt(10000) + 5000).map(_.millis))
      semaphore <- Semaphore[IO](5)
      _ <- durations.parTraverse(heavyMethod(semaphore))
    } yield ExitCode.Success
}

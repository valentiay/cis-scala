package seminars.seminar8

import java.time.{Instant, ZoneId, ZonedDateTime}
import java.time.temporal.ChronoField
import java.util.concurrent.TimeUnit

import cats.implicits._
import cats.effect.{Clock, ExitCode, IO, IOApp, Timer}

import scala.concurrent.duration._

object sleep extends IOApp {
  def millisTillTick(tickIntervalMs: Int, zonedDateTimeNow: ZonedDateTime): FiniteDuration = {
    val secondOfMinute = zonedDateTimeNow.get(ChronoField.SECOND_OF_MINUTE)
    val milliOfSecond = zonedDateTimeNow.get(ChronoField.MILLI_OF_SECOND)
    val millisTillTick = tickIntervalMs - (secondOfMinute * 1000 + milliOfSecond) % tickIntervalMs
    millisTillTick.millis
  }

  def tick(tickIntervalMs: Int): IO[Unit] =
    for {
      now <- Clock[IO].realTime(TimeUnit.MILLISECONDS)
      zonedDateTimeNow = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault)
      _ <- IO(println(s"[$tickIntervalMs] $zonedDateTimeNow"))
      // Тайпкласс Timer позволяет откладывать действия во времени.
      // Из Timer также можно получить экземпляр тайпкласса Clock через метод Timer[IO].clock
      _ <- Timer[IO].sleep(millisTillTick(tickIntervalMs, zonedDateTimeNow))
      // IO можно вызывать рекурсивно (не обязательно хвосторекурсивно) без переполнения стэка
      _ <- tick(tickIntervalMs)
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    // .as(<value>) синоним для .map(_ => <value>)
    List(1000, 5000, 10000, 15000, 20000, 30000, 40000, 50000, 60000).parTraverse(tick).as(ExitCode.Success)
}

object timeout extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    // IO.never - IO, который никогда не завершится
    // .timeout - выбрасывает ошибку, если IO выполнялось дольше указанного времени
    IO.never.timeout(5.seconds).as(ExitCode.Success)
}

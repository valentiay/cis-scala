package seminars.seminar8

import java.time.{Instant, ZoneId, ZonedDateTime}
import java.time.temporal.ChronoField
import java.util.concurrent.TimeUnit

import cats.effect.{Clock, ExitCode, IO, IOApp, Timer}

import scala.concurrent.duration._

object sleep extends IOApp {
  val tickIntervalMs = 10

  def millisTillTick(zonedDateTimeNow: ZonedDateTime): FiniteDuration = {
    val secondOfMinute = zonedDateTimeNow.get(ChronoField.SECOND_OF_MINUTE)
    val milliOfSecond = zonedDateTimeNow.get(ChronoField.MILLI_OF_SECOND)
    val millisTillTick = tickIntervalMs - (secondOfMinute * 1000 + milliOfSecond) % tickIntervalMs
    millisTillTick.millis
  }

  val tick: IO[Unit] =
    for {
      now <- Clock[IO].realTime(TimeUnit.MILLISECONDS)
      zonedDateTimeNow = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault)
      _ <- IO(println(zonedDateTimeNow))
      // Тайпкласс Timer позволяет откладывать действия во времени.
      // Из Timer также можно получить экземпляр тайпкласса Clock через метод Timer[IO].clock
      _ <- Timer[IO].sleep(millisTillTick(zonedDateTimeNow))
      // IO можно вызывать рекурсивно (не обязательно хвосторекурсивно) без переполнения стэка
      _ <- tick
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    // .as(<value>) синоним для .map(_ => <value>)
    tick.as(ExitCode.Success)
}

object timeout extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    // IO.never - IO, который никогда не завершится
    // .timeout - выбрасывает ошибку, если IO выполнялось дольше указанного времени
    IO.never.timeout(5.seconds).as(ExitCode.Success)
}

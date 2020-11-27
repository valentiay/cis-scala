package homeworks.homework3

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, ExitCode, IO, IOApp}

import scala.util.Random
import scala.concurrent.duration._

object task1 extends IOApp {

  // Реализуйте функцию map3, которая комбинирует значения из трех IO в одно с помощью функции f (см. примеры).
  //
  // Продвинутая версия (не обязательно): вычислять IO[A], IO[B], IO[C] одновременно, а не последовательно
  def map3[A, B, C, D](fa: IO[A], fb: IO[B], fc: IO[C])(f: (A, B, C) => D): IO[D] = ???


  // Служебные функции для примеров
  val randomString: IO[String] =
    IO{
      val string = Random.alphanumeric.take(Random.nextInt(4) + 1).mkString
      println(string)
      string
    }

  def sleep(seconds: Int): IO[Int] =
    IO.sleep(seconds.seconds).as(seconds)

  // Примеры
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(Random.setSeed(42))
      res1 <- map3(randomString, randomString, randomString)((a, b, c) => a + b + c)
      _ <- IO(println(res1))
      _ <- IO(println())
      // pi2
      // 7D
      // XDi
      // pi27DXDi
      //
      // Конкретные значения могут различаться, но последняя строка должна быть конкатенацией первых трех

      startTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      res2 <- map3(sleep(1), sleep(2), sleep(3))(_ * _ * _)
      endTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      _ <- IO(println(res2))
      // 6
      _ <- IO(println(s"${endTime - startTime} millis"))
      // Для продвинутой версии - ~3000 millis, для обычной версии - ~6000 millis
    } yield ExitCode.Success
}

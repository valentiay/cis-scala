package seminars.seminar10

import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream

import scala.concurrent.duration._
import scala.util.Random

object compile extends IOApp {

  def printInt(int: Int): IO[Unit] =
    IO(println(int))

  // .flatTap - для каждого вычисленного элемента стрима произвести side-эффект
  val stream: Stream[IO, Int] = Stream(0, 1, 2).evalTap(printInt)

  def run(args: List[String]): IO[ExitCode] =
    for {
      // .drain - проигнорировать результаты стрима, но вычислить все элементы и произвести все side-эффекты
      _ <- IO(println("\ndrain"))
      drain <- stream.compile.drain
      _ <- IO(println(drain))

      // .last - получить последний элемент (если он есть), вычислив все элементы
      _ <- IO(println("\nlast"))
      last <- stream.compile.last
      _ <- IO(println(last))

      // .toList - вычислить все элементы списка и записать их в список
      _ <- IO(println("\ntoList"))
      toList <- stream.compile.toList
      _ <- IO(println(toList))

      // .fold - "свернуть" все элементы с помощью начального значения и функции
      _ <- IO(println("\nfold"))
      fold <- stream.compile.fold("")((str, int) => str + int)
      _ <- IO(println(fold))

    } yield ExitCode.Success
}

object create extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      // .iterate - создать бесконечный стрим, получая следующий элемент из предыдущего
      _ <- IO(println("\niterate"))
      iterate = Stream.iterate(1)(_ * 2).take(10).compile.toList
      _ <- IO(println(iterate))

      // .repeatEval - создать бесконечный стрим, вычисляя каждый элемент из эффекта
      // .eval - создать стрим с одним элементом, который вычислится из эффекта
      _ <- IO(println("\nrepeatEval"))
      repeatEval <- Stream.repeatEval(IO(Random.nextInt(100))).take(10).compile.toList
      _ <- IO(println(repeatEval))

      // .empty - пустрой стрим
      _ <- IO(println("\nempty"))
      empty = Stream.empty.compile.toList
      _ <- IO(println(empty))

      // .constant - пустрой стрим
      _ <- IO(println("\nconstant"))
      constant = Stream.constant(10).take(20).toList
      _ <- IO(println(constant))

      // .fixedRate - бесконечный стрим, который создает новый элемент типа unit через фиксированные промежутки времени
      // .random - бесконечный стрим, возвращающий случайные числа
      // .zipRight - когда поступают оба значения, возвращает значение из стрима справа (переданного в метод)
      _ <- IO(println("\nfixedRate + zipRight + random"))
      _ <- Stream
        .fixedRate[IO](5.seconds)
        .zipRight(Stream.random[IO])
        .evalTap(int => IO(println(int)))
        .take(12)
        .compile
        .drain
    } yield ExitCode.Success
}

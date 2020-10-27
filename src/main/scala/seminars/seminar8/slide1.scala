package seminars.seminar8

import cats.effect.{ContextShift, ExitCode, IO, IOApp}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

object runAsync extends App {
  import cats.implicits._
  // ContextShift - в каком-то смысле аналог ExecutionContext для IO.
  // Нужен для выполнения параллельных действий
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  def ioN(n: Int): IO[Unit] =
    // IO.apply или IO.delay откладывает какое-то вычисление, которое может производить side эффекты
    // IO не запускает каких-либо вычислений, только описывает их
    IO(println(s"Effect $n!"))

  val io0 = ioN(0)

  println("Start")

  (
    for {
      _ <- io0
      _ <- io0
      _ <- (1 to 10).toList.parTraverse(ioN)
    } yield ()
  ).unsafeRunTimed(10.seconds)
}

object futureRunAsync extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  def futureN(n: Int): Future[Unit] =
    Future(println(s"Effect $n!"))

  val future0 = futureN(0)

  println("Start")

  Await.ready(
    for {
      _ <- future0
      _ <- future0
      _ <- Future.traverse(1 to 10)(futureN)
    } yield (),
    10.seconds
  )
}

// IOApp - удобный способ для запуска приложений с IO.
// При его использвании создаются все нужные имплиситы
object ioApp extends IOApp {
  import cats.implicits._

  def ioN(n: Int): IO[Unit] =
    IO(println(s"Effect $n!"))

  val io0 = ioN(0)

  println("Start")

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- io0
      _ <- io0
      _ <- (1 to 10).toList.parTraverse(ioN)
    } yield ExitCode.Success
}

object toFuture extends App {
  IO(println("Effect!")).unsafeToFuture()
}

object fromFuture extends IOApp {
  import scala.concurrent.ExecutionContext.Implicits.global

  def someLegacyMethod(x: Int): Future[String] =
    Future{
      println("Constructing string")
      "E" + "f" * x + "ect!"
    }

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("Start"))
      value <- IO.fromFuture(IO(someLegacyMethod(10)))
      _ <- IO(println(value))
    } yield ExitCode.Success
}
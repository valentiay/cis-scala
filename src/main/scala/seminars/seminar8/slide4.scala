package seminars.seminar8

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, ExitCode, IO, IOApp}

import scala.util.Random

object slide4 extends IOApp {
  @scala.annotation.tailrec
  def merge(
             vector1: Vector[Int],
             vector2: Vector[Int],
             result: Vector[Int] = Vector.empty,
             idx1: Int = 0,
             idx2: Int = 0
           ): Vector[Int] = {
    if (idx1 == vector1.size) {
      result ++ vector2.drop(idx2)
    } else if (idx2 == vector2.size) {
      result ++ vector1.drop(idx1)
    } else if (vector1(idx1) < vector2(idx2)) {
      merge(vector1, vector2, result :+ vector1(idx1), idx1 + 1, idx2)
    } else {
      merge(vector1, vector2, result :+ vector2(idx2), idx1, idx2 + 1)
    }
  }

  def mergeSort(vector: Vector[Int]): Vector[Int] = {
    if (vector.size == 1) {
      vector
    } else {
      val (left, right) = vector.splitAt(vector.size / 2)
      val sortedLeft = mergeSort(left)
      val sortedRight = mergeSort(right)
      merge(sortedLeft, sortedRight)
    }
  }

  def mergeSortF(vector: Vector[Int]): IO[Vector[Int]] = {
    if (vector.size == 1) {
      // В IO.pure передается значение, которое вычисляется без side-эффектов
      // В IO.raiseError аналогично можно передать ошибку
      IO.pure(vector)
    } else if (vector.size < 10000) {
      IO.pure(mergeSort(vector))
    } else {
      val (left, right) = vector.splitAt(vector.size / 2)
      for {
        // .start запускает процесс асинхронно
        leftFiber <- mergeSortF(left).start
        rightFiber <- mergeSortF(right).start
        sortedLeft <- leftFiber.join
        sortedRight <- rightFiber.join
      } yield merge(sortedLeft, sortedRight)
    }
  }

  def benchmark(fa: IO[Vector[Int]]): IO[Long] =
    for {
      startTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      _ <- fa
      endTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
    } yield endTime - startTime

  def run(args: List[String]): IO[ExitCode] =
    for {
      data <- IO(Vector.fill(1000000)(Random.nextInt()))
      pureTime <- benchmark(IO(mergeSort(data)))
      _ <- IO(println(pureTime))
      ioTime <- benchmark(mergeSortF(data))
      _ <- IO(println(ioTime))
    } yield ExitCode.Success
}

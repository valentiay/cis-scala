package seminars.seminar7

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Random, Success}

object callbacks extends App {

  import scala.concurrent.ExecutionContext.Implicits.global
  val findNeedle = Future {
    ("hay" * 200000 + "needle" + "hay" * 200).indexOf("needle")
  }.flatMap { index =>
    if (index < 0) {
      Future.failed(new NoSuchElementException())
    } else {
      Future.successful(index)
    }
  }

  val successful = Future.successful(1)
  val failed = Future.failed(new NoSuchElementException())

  println(findNeedle)

  // Await.result бросит исключение, если результат - ошибка. Просто подождать завершения - Await.ready
  val result = Await.ready(findNeedle, Duration.Inf)
  println(s"Await.ready: $result")

  // Коллбэки могут вызываться в случайном порядке
  // Если в каком-то из коллбеков есть блокирующий вызов, то остальные могут не завершиться до окончания этого вызова
  findNeedle.onComplete {
    case Failure(exception) => println(s"[Callback1] Error occurred: $exception")
    case Success(value)     => println(s"[Callback1] Found: $value")
  }

  findNeedle.onComplete {
    case Failure(exception) => println(s"[Callback2] Error occurred: $exception")
    case Success(value)     => println(s"[Callback2] Found: $value")
  }

  findNeedle.foreach(value => println(s"[Callback3] Found: $value"))
}

object promise extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val p: Promise[List[Int]] = Promise()

  val producer = Future {
    val ints = List.fill(20000)(Random.nextInt(3))
    if (ints.head == 1) {
      // Бросает исключение, если промис уже закомпличен (если такое возможно, то можно использовать trySuccess)
      p.success(ints)
    } else {
      // Аналогично, есть tryFailure
      p.failure(new IllegalStateException("Ooops, first element must be 1"))
    }
  }

  val consumer = p.future.map { ints =>
    ints.take(200)
  }

  println(Await.ready(consumer, Duration.Inf))
}

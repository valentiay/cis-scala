package seminars.seminar7

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object errors extends App {
  def divide(a: Long, b: Long)(implicit ec: ExecutionContext): Future[(Long, Long)] =
    Future {
      (a / b, a % b)
    }

  def safeDivide(a: Long, b: Long)(implicit ec: ExecutionContext): Future[(Long, Long)] =
    divide(a, b).recoverWith {
      case err: ArithmeticException =>
        if (a > 0) {
          Future.successful((Long.MaxValue, 0))
        } else if (a < 0) {
          Future.successful((Long.MinValue, 0))
        } else {
          Future.failed(new ArithmeticException("0 / 0"))
        }
    }

  import scala.concurrent.ExecutionContext.Implicits.global

  println(Await.ready(safeDivide(0, 0), Duration.Inf))

  println(Await.ready(divide(10, 0), Duration.Inf))
}

@SuppressWarnings(Array("org.wartremover.warts.All"))
object critical extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val fut = Future {
    throw new OutOfMemoryError()
  }

  println(Await.ready(fut, Duration.Inf))
}

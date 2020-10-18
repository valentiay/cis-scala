package seminars.seminar7

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{Await, ExecutionContext, Future, Promise, blocking}
import scala.concurrent.duration._
import cats.instances.future._

object blocking1 extends App {
  def blockingOperation(id: Int, millis: Long)(implicit ec: ExecutionContext): Future[Int] =
    Future {
      println(s"[$id] Going asleep for ${millis / 1000}s")
      Thread.sleep(millis)
      println(s"[$id] Woke up")
      id
    }

  def blockingOps(implicit ec: ExecutionContext) =
    Future.sequence(for (i <- 1 to 16) yield blockingOperation(i, 10000))

  def somethingUseful(implicit ec: ExecutionContext) = Future {
    println("Doing something useful")
    2 + 2
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  val bo = blockingOps
  val su = somethingUseful

  Await.result(bo, Duration.Inf)
  Await.result(su, Duration.Inf)
}

object blocking2 extends App {
  def wrappedBlockingOperation(id: Int, millis: Long)(implicit ec: ExecutionContext): Future[Int] =
    Future {
      blocking { // Работает не для всех контекстов (https://docs.scala-lang.org/overviews/core/futures.html#blocking-inside-a-future)
        println(s"[$id] Going asleep for ${millis / 1000}s")
        Thread.sleep(millis)
        println(s"[$id] Woke up")
        id
      }
    }

  def blockingOps(implicit ec: ExecutionContext) =
    Future.sequence(for (i <- 1 to 260) yield wrappedBlockingOperation(i, 10000))

  def somethingUseful(implicit ec: ExecutionContext) = Future {
    println("Doing something useful")
    2 + 2
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  val bo = blockingOps
  val su = somethingUseful

  Await.result(bo, Duration.Inf)
  Await.result(su, Duration.Inf)
}

object blocking3 extends App {

  def blockingOperation(id: Int, millis: Long)(implicit ec: ExecutionContext): Future[Int] =
    Future {
      println(s"[$id] Going asleep for ${millis / 1000}s")
      Thread.sleep(millis)
      println(s"[$id] Woke up")
      id
    }

  def blockingOps(implicit ec: ExecutionContext) =
    Future.sequence(for (i <- 1 to 1000) yield blockingOperation(i, 10000))

  def somethingUseful(implicit ec: ExecutionContext) = Future {
    println("Doing something useful")
    2 + 2
  }

  val unboundedExecutor = Executors.newCachedThreadPool()
  val fixedExecutor     = Executors.newFixedThreadPool(4)

  val blockingContext: ExecutionContext = ExecutionContext.fromExecutor(unboundedExecutor)
  val regularContext: ExecutionContext  = ExecutionContext.fromExecutor(fixedExecutor)

  val bo = blockingOps(blockingContext)
  val su = somethingUseful(regularContext)

  Await.result(bo, Duration.Inf)
  Await.result(su, Duration.Inf)

  unboundedExecutor.shutdown()
  fixedExecutor.shutdown()
}

object async extends App {
  class ScheduledExecutionContext(executor: ScheduledExecutorService) extends ExecutionContext {
    private val wrapped = ExecutionContext.fromExecutor(executor)

    def reportFailure(cause: Throwable): Unit = wrapped.reportFailure(cause)
    def execute(runnable: Runnable): Unit     = wrapped.execute(runnable)

    def delay[A](action: => A, duration: Duration): Future[A] = {
      val p = Promise[A]()
      executor.schedule(() => {
        p.success(action)
      }, duration.toMillis, TimeUnit.MILLISECONDS)

      p.future
    }
  }

  def asyncOperation(id: Int, millis: Long)(implicit ec: ScheduledExecutionContext): Future[Int] =
    Future {
      println(s"[$id] Going asleep for ${millis / 1000}s")
    }.flatMap { _ =>
      ec.delay(println(s"[$id] Woke up"), millis.millis)
    }.map(_ => id)

  def asyncOps(implicit ec: ScheduledExecutionContext) =
    Future.sequence(for (i <- 1 to 1000000) yield asyncOperation(i, 20000))

  def somethingUseful(implicit ec: ExecutionContext) = Future {
    println("Doing something useful")
    2 + 2
  }

  val executor                               = Executors.newSingleThreadScheduledExecutor()
  implicit val ec: ScheduledExecutionContext = new ScheduledExecutionContext(executor)

  val ao = asyncOps
  val su = somethingUseful

  Await.result(ao, Duration.Inf)
  Await.result(su, Duration.Inf)
  executor.shutdown()
}

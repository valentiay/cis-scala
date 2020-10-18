package seminars.seminar7

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object complex extends App {
  def getUser(id: Long)(implicit ec: ExecutionContext): Future[String] =
    if (id == 0) {
      Future {
        Thread.sleep(500)
        println(s"Getting user $id")
        "Oleg"
      }
    } else {
      Future.failed(new NoSuchElementException(s"User not found: $id"))
    }

  def getPost(id: Long)(implicit ec: ExecutionContext): Future[String] =
    id match {
      case 0 =>
        Future {
          println("Getting post 0")
          "Futures are eager"
        }
      case 1 =>
        Future {
          println("Getting post 1")
          "Futures require execution context everywhere"
        }
      case _ =>
        Future.failed(new NoSuchElementException(s"Post not found: $id"))
    }

  def getPostIds(implicit ec: ExecutionContext): Future[List[Long]] =
    Future {
      println("Getting post ids")
      List(0, 1)
    }

  def showPosts1(userId: Long)(implicit executionContext: ExecutionContext): Future[String] =
    for {
      user    <- getUser(userId)
      postIds <- getPostIds
      posts   <- Future.traverse(postIds)(getPost)
    } yield s"""
               |Hi, $user!
               |Here are some posts:
               |${posts.mkString("\n")}
               |""".stripMargin

  def showPosts2(userId: Long)(implicit executionContext: ExecutionContext): Future[String] = {
    val userF    = getUser(userId)
    val postIdsF = getPostIds
    for {
      user    <- userF
      postIds <- postIdsF
      posts   <- Future.traverse(postIds)(getPost)
    } yield s"""
               |Hi, $user!
               |Here are some posts:
               |${posts.mkString("\n")}
               |""".stripMargin
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  println(Await.result(showPosts1(0), Duration.Inf))
  println(Await.result(showPosts2(0), Duration.Inf))
}


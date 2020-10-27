package seminars.seminar8

import java.io.{BufferedReader, InputStreamReader}
import java.net.URL
import java.util.concurrent.{ExecutorService, Executors, TimeUnit}
import java.util.stream.Collectors

import cats.effect.{Blocker, Clock, ExitCode, IO, IOApp}
import cats.implicits._

import scala.concurrent.ExecutionContext

object slide2 extends IOApp {

  val websites =
    List(
      "https://google.com",
      "https://youtube.com",
      "https://amazon.com",
      "https://facebook.com",
      "https://yahoo.com",
      "https://reddit.com",
      "https://wikipedia.org",
      "https://ebay.com",
      "https://bing.com",
      "https://netflix.com",
      "https://twitch.com",
      "https://instagram.com",
      "https://yandex.ru",
      "https://tinkoff.ru",
      "https://mail.ru",
    )

  final case class RequestStats(url: String, length: Int, time: Long) {
    def format: String = {
      val foramttedUrl = url + " " * (25 - url.length)
      val formattedLength = " " * (10 - length.toString.length) + length.toString + " bytes"
      val formattedTime = " " * (8 - time.toString.length) + time.toString + " ms"
      foramttedUrl + "|" + formattedLength + "|" + formattedTime
    }
  }

  // Создание класса Blocker для работы с блокирующим функционалом
  val blockingExecutor = Executors.newFixedThreadPool(64)
  val blockingContext = ExecutionContext.fromExecutor(blockingExecutor)
  val blocker = Blocker.liftExecutionContext(blockingContext)

  def httpRequest(url: String): String = {
    println(s"GET $url")
    // final URLConnecton connecction = new URL(url).openConnection()
    val connection = new URL(url).openConnection
    connection.setRequestProperty("Accept-Charset", "UTF-8")
    val is = connection.getInputStream
    new BufferedReader(new InputStreamReader(is))
      .lines().parallel().collect(Collectors.joining("\n"))
  }

  def httpRequestStats(url: String): IO[RequestStats] =
    for {
      // Тайпкласс Clock позволяет получать истиное или монотонное время
      startTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
      // Использование класса Blocker при блокирующем вызове
      response <- blocker
        .blockOn(IO(httpRequest(url)))
        .handleErrorWith(error =>
          IO(println(s"$error happened for $url")).as("")
        )
      endTime <- Clock[IO].monotonic(TimeUnit.MILLISECONDS)
    } yield RequestStats(url, response.length, endTime - startTime)


  def run(args: List[String]): IO[ExitCode] =
    for {
      stats <- websites.parTraverse(httpRequestStats)
      // Все side-эффекты должны быть отложены через IO.apply или IO.delay (два синонимичных метода)
      _ <- IO(println(stats.map(_.format).mkString("\n")))
      _ <- IO(blockingExecutor.shutdown())
    } yield ExitCode.Success
}

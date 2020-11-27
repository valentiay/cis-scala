package homeworks.homework3

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object task2 extends IOApp {

  final case class City(name: String, priority: Int)

  final case class Error(text: String) extends Throwable

  def getCity(code: String): IO[City] = {
    Map(
      "MOW" -> City("Москва", 10),
      "LED" -> City("Санкт-Петербург", 9),
      "OVB" -> City("Новосибирск", 8),
      "SVX" -> City("Екатеринбург", 7),
      "KZN" -> City("Казань", 6),
      "GOJ" -> City("Нижний Новгород", 5),
      "CEK" -> City("Челябинск", 4),
      "KUF" -> City("Самара", 3),
      "OMS" -> City("Омск", 2),
      "ROV" -> City("Ростов-на-Дону", 1)
    ).get(code) match {
      case Some(value) => IO.pure(value)
      case None => IO.raiseError(Error(s"No such city: $code"))
    }
  }

  // Реализуйте метод getCities, который для списка кодов возвращает соответствующие города.
  // Города в ответе должны быть отсортированы по приоритету (City#priority), сначала - с наибольшим приоритетом.
  // Один город можно получить через метод getCity.
  // В случае ошибки нужно вернуть первую ошибку из списка.
  //
  // Hint: воспользуетесь методом traverse, который доступен благодаря импорту cats.implicits._
  // Примеры - .parTraverse из семинара 8
  def getCities(codes: List[String]): IO[List[City]] = ???

  def run(args: List[String]): IO[ExitCode] =
    for {
      cities <- getCities(List("KUF", "ROV", "LED"))
      _ <- IO(println(cities.map(_.name).mkString("\n")))
      // Санкт-Петербург
      // Самара
      // Ростов-на-Дону
    } yield ExitCode.Success
}

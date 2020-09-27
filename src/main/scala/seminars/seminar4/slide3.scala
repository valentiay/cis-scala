package seminars.seminar4

import scala.util.{Try, Success, Failure}

object slide3 extends App {
  // Два конструктора: успех (Success) и ошибка (Failure)
  val success: Try[String] = Success("Hola!")
  val failure: Try[String] = Failure(new RuntimeException("Not today"))

  println(success)
  println(s"success: ${success.isSuccess}")

  println(failure)
  println(s"failure: ${failure.isFailure}")

  println()

  val emptyList = List.empty[Int]
  val head = Try(emptyList.head) // не бросает ошибку!
  println(head)

  def getNumber(optNumber: Option[Int]): Int =
    Try(optNumber.get) match {
      case Success(x) => x
      case Failure(exc) =>
        println(s"There was an exception $exc")
        0
    }

  println(getNumber(Some(42)))
  println(getNumber(None))

  val number = Try(emptyList.head).getOrElse(0)
  println(number)

  val numberString: String =
    Try(emptyList.head).fold(e => e.toString, x => x.toString)
  println(numberString)

  val list = List(0)
  val tryHead = Try(list.head)

  println(tryHead)

  println(tryHead.filter(_ > 0))

  println(tryHead.collect {
    case x if x % 2 == 1 => x.toString
  })

  println(tryHead.map(_ / 0))

  println(tryHead.flatMap(x => Success(list(x + 1))))

  println(tryHead.toOption)

  println(tryHead.toEither)
}

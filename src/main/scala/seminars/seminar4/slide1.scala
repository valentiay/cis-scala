package seminars.seminar4

case class Error(message: String)

object eitherBasics extends App {

  // Either[+A, +B] - тип, который содержит в себе либо значение типа A, либо значение типа B.
  // Имеет две реализации:
  //  * Left[+A, +B] - содержит в себе значение типа A (тот, что слева)
  //  * Right[+A, +B] - содержит в себе значение типа B (тот, что справа)
  // Наиболее частое использование Either - обработка ошибок. Тип A (левый) - это ошибка,
  // а тип B (правый) - значение, полученное при успешном выполнении
  val errorOrSuccess1: Either[Error, String] = Right("I'm a string")
  val errorOrSuccess2: Either[Error, String] = Left(Error("something gone wrong"))
  val errorOrSuccess3: Error Either String   = Left(Error("something gone wrong again"))
  println(errorOrSuccess1)
  println(errorOrSuccess2)
  println(errorOrSuccess3)
  println()


  // Either, как и Option, это sealed-class. Его тоже можно испльзовать в pattern-matching.
  def handleError(errorOrSucess: Either[Error, String]): Unit =
    errorOrSucess match {
      case Right(value)         => println(s"Success: $value")
      case Left(Error(message)) => println(s"Error occurred: $message")
    }

  handleError(errorOrSuccess1)
  handleError(errorOrSuccess2)
}

object eitherTransform extends App {
  // "Правое" значение Either можно преобразовать методом .map
  val reverse: String => String =
    str => str.reverse
  println("map")
  println(Right("123").map(reverse))
  println(Left("error!!!").map(reverse))
  println()


  // "Правое" значение можно преобразовать и функцией, которая возвращает Either, с помощью .flatMap
  val toInt: String => Either[Error, Int] =
    str => str.toIntOption.toRight(Error("It's not an Int"))

  println("flatMap")
  println(Right("123").flatMap(toInt))
  println(Right("Not an int").flatMap(toInt))
  println(Left(Error("It's already an error")).flatMap(toInt))
  println()

  // Either можно использовать в for-comprehension
  def getAmountFromPrice(price: String): Either[Error, String] =
    price.split(' ') match {
      case Array(amount, currency) => Right(amount)
      case _                       => Left(Error(s"'$price' is not a price"))
    }

  def parseAmount(price: String): Either[Error, Int] =
    for {
      amountString <- getAmountFromPrice(price)
      amount       <- toInt(amountString)
    } yield amount

  println("for-comprehension")
  println(parseAmount("1 EUR"))
  println(parseAmount("notanamount"))
  println(parseAmount("one EUR"))
}

object eitherCheck extends App {
  println("isRight/isLeft")
  println(Right("123").isRight)
  println(Right("123").isLeft)

  println(Right("123").contains("123"))
  println(Left("123").contains("123"))
  // exists, forall etc.
}
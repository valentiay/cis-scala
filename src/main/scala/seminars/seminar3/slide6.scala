package seminars.seminar3

object optionBasics extends App {
  // Option[+A] - тип для обозначения опциональных значний.
  // Имеет две реализации:
  //  * Some[+A] - для случаев, когда значение есть
  //  * None - для случаев, когда значения нет
  val maybeString1: Option[String] = Some("42")
  val maybeString2: Option[String] = None

  println(maybeString1)
  println(maybeString2)
  println()


  // Т.к. Option - это sealed class, при работе с ним можно использовать pattern-matching
  def handleOption[A](maybeA: Option[A]): Unit =
    maybeA match {
      case Some(a) => println(s"Got something: $a")
      case None => println("Got nothing")
    }

  println("pattern matching")
  handleOption(Some("42"))
  handleOption(None)
  println()


  // Option может обрабатывать null-pointer:
  println("null")
  println(Option("Not null"))
  println(Option(null))
  println()

}

object optionTransform extends App {
  // Значение внутри Option можно изменить методом map
  val getAnswer: String => String =
    str => s"The answer is $str"

  println("map")
  println(Some("42").map(getAnswer))
  println(None.map(getAnswer))
  println()


  // Также, для преобразований можно использовать функции, которые возвращают Option
  val toIntOption: String => Option[Int] =
    str => str.toIntOption

  println("flatMap")
  println(Some("42").flatMap(toIntOption))
  println(Some("I am not a number").flatMap(toIntOption))
  println(None.flatMap(toIntOption))
  println()


  // Значения внутри Option можно фильтровать
  val hasTwoChars: String => Boolean =
    str => str.length == 2

  println("filter")
  println(Some("42").filter(hasTwoChars))
  println(Some("123").filter(hasTwoChars))
  println()


  // Т.к. есть методы map и flatMap, Option можно использовать в for-comprehension
  def firstCharToDoubledInt(str: String): Option[Int] =
    for {
      firstChar <- str.headOption
      int <- firstChar.toString.toIntOption
    } yield 2 * int

  println(firstCharToDoubledInt("12345"))
  println(firstCharToDoubledInt("hello"))

  def firstCharToDoubledIntUnsugared(str: String): Option[Int] =
    str.headOption
      .flatMap(firstChar => firstChar.toString.toIntOption)
      .map(int => 2 * int)

  println(firstCharToDoubledIntUnsugared("12345"))
  println(firstCharToDoubledIntUnsugared("hello"))
}

object optionCheck extends App {
  // Можно проверять Option на пустоту
  println("isDefined/isEmpty")
  println(Some("42").isEmpty)
  println(None.isEmpty)
  println(Some("42").isDefined)
  println(None.isDefined)
  println()


  // Можно проверять, что находится внутри
  println("contains")
  println(Some("42").contains("42"))
  println(Some("123").contains("42"))
  println(None.contains("42"))
  println()


  // Есть методы forall и exists. Они отличаются тем, что у None метод forall возвращает true, а exists - false
  val hasTwoChars: String => Boolean =
    str => str.length == 2

  println("forall/exists")
  println(Some("42").exists(hasTwoChars))
  println(Some("42").forall(hasTwoChars))
  println(None.forall(hasTwoChars))
  println(None.exists(hasTwoChars))
}

object optionGet extends App {
  // Метод .get возвращает значение, если оно есть, но может бросить исключение
  println("get")
  println(Option("42").get)
  // println(None.get) // Exception!!!
  println()


  // Лучше использовать метод .getOrElse, в котором можно указать значение по умолчанию. Он не бросает исключения
  println("getOrElse")
  println(Option("42").getOrElse("123"))
  println(None.getOrElse("123"))
  println()


  // На случай, если значение по умолчанию тоже опциональное, есть метод .orElse
  val maybeDefault1: Option[String] = Some("123")
  val maybeDefault2: Option[String] = None
  println("orElse")
  println(None.orElse(maybeDefault1))
  println(None.orElse(maybeDefault2))
  println()

  // Использовать значение, находящееся внутри Option, для side-эффекта можно с помощью метода .foreach
  Some("123").foreach(println)
  None.foreach(println)
}
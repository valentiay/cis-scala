package seminars.seminar2

object slide4 extends App {

  // Пример части методов, которые генерируются для
  // final case class Name(first: String, last: String)
  final class Name(val first: String, val last: String) {
    override def toString: String =
      s"Name($first, $last)"

    override def equals(obj: Any): Boolean =
      obj match {
        case other: Name =>
          other.first == first && other.last == last
        case _ =>
          false
      }

    def copy(first: String = first, last: String = last): Name =
      new Name(first, last)
  }

  // Объект-компаньон класса Name. Является синглтоном, нельзя создать вручную через new.
  // Все методы объекта - аналоги статичных методов в Java
  object Name {
    def apply(first: String, last: String) =
      new Name(first, last)
  }

  val name1 = Name("Vasia", "Pupkin")
  val name2 = Name("Alexander", "Valentinov")
  println(name1)
  println(name1 == name2)
  println(name1 == name1)
  println(name2.copy(last = "Pushkin"))
}


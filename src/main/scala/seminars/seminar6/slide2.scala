package seminars.seminar6

object explicitMonoid extends App {

  def reduceInts(ints: List[Int]): Int =
    ints.fold(0)(_ + _)

  def multiplyInts(n: Int, int: Int): Int =
    int * n


  def reduceStrings(strings: List[String]): String =
    strings.fold("")(_ + _)

  def multiplyStrings(n: Int, string: String): String =
    string * n


  def reduceBooleans(booleans: List[Boolean]): Boolean =
    booleans.fold(true)(_ && _)


  println(reduceInts(List(1, 3, 5, 2, 4)))
  println(multiplyInts(5, 10))
  println(reduceStrings(List("A", "C", "E", "B", "D")))
  println(multiplyStrings(15, "A"))
  println(reduceBooleans(List(true, false, false)))
}

object implicitMonoid extends App {

  // Наличие неявного типа Monoid[T] в области видимости означает,
  // что для типа T существует бинарная операция (combine)
  // и нейтральный элемент относительно этой операции (zero).
  // Такой паттерн называется классом типов (typeclass)
  trait Monoid[T] {
    def zero: T
    def combine(a: T, b: T): T
  }

  object Monoid {

    // Компилятор ищет неявные параметры в объектах-компаньонах всех связанных с неявным параметром типов.
    // implicit object - сопособ объявить неявное значение, удобный при работе с классами типов
    implicit object IntMonoid extends Monoid[Int] {
      val zero: Int = 0
      def combine(a: Int, b: Int): Int = a + b
    }

    implicit object StringMonoid extends Monoid[String] {
      val zero: String = ""
      def combine(a: String, b: String): String = a + b
    }

    implicit object BooleanMonoid extends Monoid[Boolean] {
      val zero: Boolean = true
      def combine(a: Boolean, b: Boolean): Boolean = a && b
    }
  }

  // Используя неявный параметр типа Monoid[T] можно реализовать полиморфные функции reduce и multiply,
  // которая будет работать для любого типа T, для которого определено неявное значение
  def reduce[T](list: List[T])(implicit monoid: Monoid[T]): T =
    list.fold(monoid.zero)(monoid.combine)

  def multiply[T](n: Int, t: T)(implicit monoid: Monoid[T]): T =
    // Неявные параметры являются неявными значениями внутри функции
    reduce(List.fill(n)(t))

  println(reduce(List(1, 3, 5, 2, 4)))
  println(multiply(5, 10))
  println(reduce(List("A", "C", "E", "B", "D")))
  println(multiply(15, "A"))
  println(reduce(List(true, false, false)))
}
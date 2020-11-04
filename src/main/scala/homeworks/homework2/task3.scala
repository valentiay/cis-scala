package homeworks.homework2

import cats.Semigroup
import cats.data.NonEmptyList

object task3 extends App {
  // Semigroup[A] - тайпкласс, который определяет ассоциативную операцию combine(x: A, y: A): A для типа A
  // Более слабый, чем Monoid - не определяет нейтральный элемент

  // Реализуйте функцию combineErrors,
  // которая принимает Either от непустого списка ошибок типа E или успеха типа A
  // и возвращает Either от ошибки типа E, полученной комбинацией ошибок из списка в начальном порядке,
  // или успеха типа A
  //
  // Большинство методов NonEmptyList работают так же, как у обычного List,
  // но некоторые операции, например, head более безопасны, так как не бросают исключений
  def combineErrors[E, A](a: Either[NonEmptyList[E], A])(implicit semigroupE: Semigroup[E]): Either[E, A] = ???

  implicit object throwableSemigroup extends Semigroup[Throwable] {
    def combine(x: Throwable, y: Throwable): Throwable =
      new Exception(s"${x.getMessage}, ${y.getMessage}")
  }

  println(combineErrors(Right(123)))
  // Right(123)
  println(combineErrors(Left(
    NonEmptyList.of(
      new IllegalArgumentException("Oops!"),
      new IllegalStateException("Oh no!"),
      new IllegalAccessError("Yikes!")
    )))
  )
  // Left(java.lang.Exception: Oops!, Oh no!, Yikes!)


  // Реализуйте функцию mergeMaps, которая объединяет два ассоциативных массива по следующим правилам:
  // * если ключ есть в обоих массивах, то в результирующем массиве у этого ключа будет значение,
  //   полученное комбинацией двух значений для этого ключа из исходных массивов
  //   (причем значение из первого массива будет первым аргументом функции combine, а значение из второго массива будет вторым)
  // * если ключ есть только в одном из массивов, то в результирующем массиве будет этот ключ c таким же значением
  // * если ключа нет ни в одном массиве, то в результирующем массиве его тоже не будет
  def mergeMaps[K, V](left: Map[K, V], right: Map[K, V])(implicit semigroupV: Semigroup[V]): Map[K, V] = ???

  println(
    mergeMaps(
      Map(1 -> "a", 2 -> "b", 3 -> "c"),
      Map(1 -> "d", 3 -> "e", 4 -> "f")
    )
  )
  // Map(1 -> ad, 3 -> ce, 4 -> f, 2 -> b)
}

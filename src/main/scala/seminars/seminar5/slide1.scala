package seminars.seminar5

// Vector[+A] - неизменяемый вектор.
// Предоставляет быстрый доступ по индексу и относительно быстрое добавление и удаление элементов в конец и в начало
object slide1 extends App {
  val vector = Vector[Int](1, 2, 3, 4, 5)

  println(vector.updated(3, 42))
  println(vector.apply(3))
  println(0 +: vector)
  println(vector :+ 6)
  println(vector.indexOf(3))
  println(vector.indexOf(10))
  println(vector.size)
  println(vector :++ vector.reverse)
  println(vector.find(_ % 2 == 0))
  println(vector.findLast(_ % 2 == 0))
  println(vector.find(_ => false))
}

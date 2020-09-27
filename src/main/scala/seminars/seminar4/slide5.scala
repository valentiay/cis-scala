package seminars.seminar4

// Vector[+A] - неизменяемый вектор.
// Предоставляет быстрый доступ по индексу и относительно быстрое добавление и удаление элементов в конец и в начало
object slide5 extends App {
  val vector = Vector(1, 2, 3, 4, 5)

  println(vector.updated(3, 42))
  println(vector(3))
  println(0 +: vector)
  println(vector :+ 6)
  println(vector.indexOf(3))
  println(vector.size)
  println(vector :++ vector.reverse)
}

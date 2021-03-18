package seminars.seminar5

import scala.util.Random

// Set[A] - неизменяемое множество. Быстрая проверка на вхождение элемента во множество
object slide2 extends App {
  val set1 = Set(1, 2, 3)
  val set2 = Set(3, 4, 5)
  println(set1.contains(2))
  println(set1(2))
  println(set1.contains(10))
  println(set1(10))
  println()

  println(set1 + 42)
  println(set1 - 2)
  println(set1 ++ set2)
  println(set1 -- set2)
  println(set1.intersect(set2))
  println()

  println(List.fill(10)(Random.nextInt(5)).toSet)
}

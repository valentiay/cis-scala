package seminars.seminar5

import cats.data.Chain

import scala.util.Random

object slide4 extends App {
  // Списки
  val list1 = List.fill(1000000)(Random.nextInt(100))
  val list2 = List.fill(1000000)(Random.nextInt(100))

  val start1 = System.nanoTime()
  list1 ::: list2
  val end1 = System.nanoTime()
  println("List")
  println(end1 - start1 + " ns")


  // Массивы
  val array1 = list1.toArray
  val array2 = list2.toArray

  val start2 = System.nanoTime()
  array1 ++ array2
  val end2 = System.nanoTime()
  println("Array")
  println(end2 - start2 + " ns")


  // Chain
  val chain1 = Chain(list1)
  val chain2 = Chain(list2)

  val start3 = System.nanoTime()
  // Быстрая конкатенация - O(1) по памяти и времени
  chain1.concat(chain2)
  val end3 = System.nanoTime()
  println("Chain")
  println(end3 - start3 + " ns")
}

package seminars.seminar5

import cats.data.Chain

import scala.util.Random

object slide4 extends App {
  val list1 = List.fill(1000000)(Random.nextInt(100))
  val list2 = List.fill(1000000)(Random.nextInt(100))

  val start1 = System.nanoTime()
  list1 ::: list2
  val end1 = System.nanoTime()
  println(end1 - start1)

  val chain1 = Chain(list1)
  val chain2 = Chain(list2)

  val start2 = System.nanoTime()
  // Быстрая конкатенация - O(1) по памяти и времени
  chain1.concat(chain2)
  val end2 = System.nanoTime()
  println(end2 - start2)
}

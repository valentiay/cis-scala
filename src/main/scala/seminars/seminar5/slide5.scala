package seminars.seminar5

import scala.util.Random

object slide5 extends App {
  val lazyList1 = LazyList(1, 2, 3, 4)
  val lazyList2 = LazyList.iterate(0)(_ + Random.nextInt(10))

  println(lazyList1)
  println(lazyList2)

  println(lazyList1.force)
  println(lazyList2.slice(1000000, 1000010).zip(lazyList2.drop(500)).map{case (a, b) => a + b}.force)
}

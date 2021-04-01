package seminars.seminar5

import scala.collection.mutable

@SuppressWarnings(Array("org.wartremover.warts.All"))
object slide6 extends App {
  val a = mutable.Buffer(1, 2, 3, 4)
  a.update(1, 2)
  val b = mutable.ListBuffer(5, 6, 7, 8)
  val c = mutable.ArrayBuffer(9, 10, 11, 12)
  val d = mutable.HashSet(1, 2, 3, 4)
  val e = mutable.TreeSet(5, 6, 7, 8)
  case class Foo(a: Int)
//  val f = mutable.TreeSet(Foo(1))
  val g = mutable.TreeMap(1 -> "A")
  val h = Array()
}

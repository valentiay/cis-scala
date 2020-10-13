package seminars.seminar6

import scala.util.Random

object order extends App {
  def sort[A](list: List[A])(implicit ordering: Ordering[A]): List[A] =
    list.foldLeft(List.empty[A]) { (acc, a) =>
      val (left, right) = acc.span(ordering.lt(_, a))
      left ::: a :: right
    }

  def nextString(): String =
    List.fill(10)(Random.nextPrintableChar()).filter(_.isLetter).mkString


  println(sort(List.fill(10)(Random.nextInt(100000))))

  println(sort(List.fill(10)(nextString())))

  import scala.math.Ordering.Double.TotalOrdering
  println(sort(List.fill(10)(Random.nextDouble())).map(_.formatted("%1.5f")))


  implicit def listOrdering[A](implicit itemOrdering: Ordering[A]): Ordering[List[A]] =
    new Ordering[List[A]] {
      def compare(xs: List[A], ys: List[A]): Int =
        xs.zip(ys).collectFirst {
          case (x, y) if itemOrdering.lt(x, y) => -1
          case (x, y) if itemOrdering.gt(x, y) => 1
        }.getOrElse(xs.length.compare(ys.length))
    }

  println()

  println(sort(List.fill(10)(List.fill(Random.nextInt(3) + 1)(Random.nextInt(3)))).mkString("\n"))
}

object numeric extends App {
  def strangeSum[A](list: List[A])(implicit numeric: Numeric[A]): A =
    list.filter(numeric.lt(list.head, _)).sum

  val ints = List.fill(10)(Random.nextInt(1000))
  println(ints)
  println(strangeSum(ints))

  println()

  val bigDecimals = List.fill(10)(BigDecimal(Random.nextDouble()) * 10000000000000L)
  println(bigDecimals)
  println(strangeSum(bigDecimals))

}
package seminars.seminar6

import scala.util.Random

object slide4 extends App {
  implicit class ListOps[A](list: List[A])(implicit fractional: Fractional[A]) {
    def average: A =
      fractional.div(list.sum, fractional.fromInt(list.length))

    def times(a: A): List[A] =
      list.map(b => fractional.times(a, b))
  }

  println(List.fill(10000)(Random.nextDouble()).average)
  println(List.fill(10000)(BigDecimal(Random.nextDouble() * 1000000000L + Random.nextDouble() * 1000000)).average)

  println(List.fill(10000)(Random.nextDouble()).times(10).average)
}

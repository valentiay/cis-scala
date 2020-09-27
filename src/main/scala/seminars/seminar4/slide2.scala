package seminars.seminar4

object slide2 extends App {
  val divideByThree: PartialFunction[Int, Int] = {
    case x if x % 3 == 0 => x / 3
  }

  println(divideByThree(27))
  //println(divideByThree(13))

  println()

  println(divideByThree.isDefinedAt(27))
  println(divideByThree.isDefinedAt(13))
}

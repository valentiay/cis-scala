package seminars.seminar4

@SuppressWarnings(Array("org.wartremover.warts.Throw"))
object slide2 extends App {
  val divideByThree: PartialFunction[Int, Int] = {
    case x if x % 3 == 0 => x / 3
  }

  println(divideByThree(27))
  //println(divideByThree(13))

  println()

  println(divideByThree.isDefinedAt(27))
  println(divideByThree.isDefinedAt(13))

  val divideByTwo = new PartialFunction[Int, Int] {
    def isDefinedAt(x: Int): Boolean = x % 2 == 0
    def apply(v1: Int): Int = if (v1 % 2 == 0) v1 / 2 else throw new IllegalArgumentException()
  }
}

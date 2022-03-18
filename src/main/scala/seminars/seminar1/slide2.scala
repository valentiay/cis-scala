package seminars.seminar1

object vals extends App {
  val intValue1: Int = 1
  val intValue2 = 2

//  intValue1 += 1

  val stringValue: String = {
    println("Effect!")
    List.iterate(1, 10)(_ * 2).mkString(", ")
  }

  println("Call 1")
  println(stringValue)
  println("Call 2")
  println(stringValue)
}

object defs extends App {
  def powers(base: Int, count: Int): String = {
    println("Effect!")
    List.iterate(1, count)(_ * base).mkString(", ")
  }

  println("Call 1")
  println(powers(3, 10))
  println("Call 2")
  println(powers(5, 10))

  println()

  def now(): Unit = {
    println(s"${System.currentTimeMillis} epoch milliseconds")
  }

  println("Call 1")
  println(now())
  Thread.sleep(20)
  println("Call 2")
  println(now())
}

object lazyVals extends App {
  lazy val stringLazyVal = {
    println("Effect!")
    List.iterate(1, 10)(_ * 7).mkString(", ")
  }

  println("Call 1")
  println(stringLazyVal)
  println("Call 2")
  println(stringLazyVal)
}

object vars extends App {
  var doubleVar = 12.34
  println(doubleVar)
  doubleVar *= 4
  println(doubleVar)
  doubleVar += 1
  println(doubleVar)
}
package seminars.seminar1

import scala.annotation.tailrec

object methodsAndFunctions extends App {
  // Метод - аналог метода внутри класса в java
  def greeter(name: String) = s"Hello, $name!"

  println(greeter("Alexander"))

  println()

  // Метод с несколькими списками параметров
  def powers(base: Int)(count: Int): String =
    List.iterate(1, count)(_ * base).mkString(", ")

  println(powers(2)(5))

  // Функция - специальный объект, который можно вызвать
  val powersOfThree: Int => String = powers(3)

  println(powersOfThree(7))

  println()

  println(
    (1 to 10).map(powersOfThree).mkString("\n")
  )

  // Преобразование метода в функцию (eta expansion)
  val greeterFunction = greeter _
}

object namedParameters extends App {
  def greet(greeting: String = "Hello", name: String = "World"): String = s"$greeting, $name!"

  println(greet())
  println(greet(name = "Oleg"))
  println(greet("Good morning", "Sergey"))
  println(
    greet(
      name = "Anton",
      greeting = "Hi",
    )
  )
  // Нельзя ставить позиционные аргументы после именованных
  // println(greet(name = "Vasily", "Goodbye"))
}

object tailRecursion extends App {

  def factorial(n: Long): Long =
    if (n <= 1) 1
    else n * factorial(n - 1)

  @tailrec
  def harmonic(n: Long, result: Double = 0): Double =
    if (n <= 0) result
    else harmonic(n - 1, result + 1.0 / n)

  @tailrec
  def fibonacci(n: Long, previous: Long = 0, current: Long = 1): Long =
    if (n <= 1) current
    else fibonacci(n - 1, current, current + previous)

  // Нехвосторекурсивная функция завершится с StackOverflowError при большом числе рекурсивных вызовов
  // println(factorial(10000))
  println(harmonic(10000))
  println(fibonacci(1000))
}

object byNameParameters extends App {
  def printIf(condition: Boolean, ifTrue: => String, ifFalse: => String): Unit =
    if (condition) {
      println(ifTrue)
    } else {
      println(ifFalse)
    }

  println(true)
  printIf(
    condition = true,
    {
      println("Calculating harmonic")
      tailRecursion.harmonic(1000000000).toString
    },
    {
      println("Calculating fibonacci")
      tailRecursion.fibonacci(10000000000L).toString
    }
  )

  println()

  println(false)
  printIf(
    condition = false,
    {
      println("Calculating harmonic")
      tailRecursion.harmonic(1000000000).toString
    },
    {
      println("Calculating fibonacci")
      tailRecursion.fibonacci(10000000000L).toString
    }
  )

  println()

  println("vals")

  val bigHarmonic = {
    println("Calculating harmonic")
    tailRecursion.harmonic(1000000000).toString
  }

  val bigFibonacci = {
    println("Calculating fibonacci")
    tailRecursion.fibonacci(10000000000L).toString
  }

  printIf(true, bigHarmonic, bigFibonacci)
}
package seminars.seminar3

import slide3.{Stack, EmptyStack}

object slide5 extends App {
  val stack: Stack[Int] = EmptyStack.push(1)

  // Из-за стирания типов Stack[Int] и Stack[String] заменяются на Stack[Any] и
  // pattern matching возвращает неправильное значение
  stack match {
    case _: Stack[String] => println("string")
    case _: Stack[Int] => println("int")
  }
}

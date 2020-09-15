package seminars.seminar2

import scala.util.Random

object ifs extends App {
  // Любой оператор if возвращает значение. В этом случае возвращается объект () типа Unit
  println(
    if (Random.nextInt % 2 == 0) {
      println("Int is even")
    } else {
      println("Int is odd")
    }
  )

  println()

  println(if (Random.nextInt % 2 == 0) "Is even" else "Is odd")

  println()

  class Dummy {
    override def equals(obj: Any): Boolean = {
      println("Equals!")
      obj.isInstanceOf[Dummy]
    }
  }

  val dummy = new Dummy()
  // Оператор == использует .equals, а не сравнение по ссылке. Тип при этом автоматически не проверяется
  println(dummy == dummy)
  println(dummy == "String")
}

object fors extends App {
  // 1 to 10 - специальная коллекция, содержащая числа от 1 до 10
  println(1 to 10)
  println(1.to(10).toList)
  println((1 to 10).isInstanceOf[AnyRef])

  println()

  // Частный случай использования for comprehension в качестве "цикла" for.
  // Фактически является проходом по специальной коллекции

  println(
    for (i <- 1 to 10) yield i
  )

  println(
    for {
      i <- 1 to 5
      j <- 1 to 10 by 2
    } yield i * j
  )

  println(
    for {
      i <- 1 to 10
      j <- 1 to 10 by 2 if j % 3 == 0
    } yield i + j
  )

  println()

  // For comprehension с side-эффектом
  for (i <- 1 until 12 by 3) println(i)
}
package seminars.seminar4

import scala.annotation.tailrec
import scala.util.Random

// List[+A] - неизменяемый односвязный список. Имеет две реализации
// * ::(head: A, tail: List[A]) - узел списка. Хранит в себе значение и ссылку на следующий элемент
// * Nil - пустой список. На этот объект ссылается узел, который хранит последний элемент списка
object listConstruction extends App {

  // Общий способ создания для многих коллекций
  val list0 = List(1, 2, 3)
  // Функциональный синтаксис для создания списка.
  // Фактически обычный вызов метода: если метод оканчивается на двоеточие,
  // то его можно без точки писать слева от объекта (list1, list2 - вызов этого метода через точку)
  val list1 = 1 :: 2 :: 3 :: Nil
  val list2 = Nil.::(3).::(2).::(1)
  val list3 = Nil.prepended(3).prepended(2).prepended(1)
  println(list0)
  println(list1)
  println(list2)
  println(list3)
  println(list0 == list1 && list1 == list2 && list2 == list3)

  println()

  val list4 = List(4, 5, 6)
  // Конкатенация двух списков
  println(list3 ::: list4) // Специфичный для списка функциональный метод
  println(list4.:::(list3)) // Он же, запись через точку
  println(list3 ++ list4) // Общий для всех коллекций метод для конкатенации
  println(list3.++(list4))

  println()

  // Заполнение списка с помощью функции. Второй аргумент передается по имени (вычисляется при каждом использовании)
  val list5 = List.fill(10) {
    println("Evaluating")
    val randomDouble = Random.nextDouble()
    val randomInt = Random.nextInt(20)
    randomInt + randomDouble
  }

  println(list5)

  val list6 = List.iterate(1, 10)(_ * 2)
  println(list6)

  val list7 = List.tabulate(10)(i => i * 2)
  println(list7)
}

object listPatternMatching extends App {
  def showList(list: List[Int]): Unit =
    list match {
      case Nil => println("No elements")
      case List(a) => println(s"One elment: $a")
      case List(a, b) => println(s"Two elements: $a, $b")
      // Альтернативная запись
      case a :: b :: c :: Nil => println(s"Three elements: $a, $b, $c")
      // Распаковка головы и хвоста списка
      case List(x, xs@_*) => println(s"$x, $xs")
      case list => println(s"Too long list ${list.size}")
    }

  showList(List.empty)
  showList(List(1))
  showList(List(1, 2))
  showList(List(1, 2, 3))
  showList(List(1, 2, 3, 4))

  @tailrec
  def findMinima(list: List[Int], results: List[Int] = Nil): List[Int] =
    list match {
      case a :: b :: c :: tail if b < a && b < c => findMinima(b :: c :: tail, b :: results)
      case _ :: b :: c :: tail => findMinima(b :: c :: tail, results)
      case _ => results
    }

  println(findMinima(List(5, 2, 3, 4, 1, 8)))
  println(findMinima(List(1, 2)))
}

object listTransform extends App {
  val list = List(0, 1, 2, 3, 4, 5, 6)

  println("map")
  val doubledList = list.map(_ * 2)
  println(doubledList)

  val alphabet = "abcdefg"
  val charList: List[Char] = list.map(el => alphabet(el))
  println(charList)

  val wordList = list.map {
    case 1 => "one"
    case 2 => "two"
    case _ => "oops"
  }
  println(wordList)
  println()

  println("filter")
  val filteredList = list.filter(_ % 2 == 0)
  println(filteredList)
  println()

  println("collect")
  val collectedList = list.collect {
    case 1 => "one"
    case 2 => "two"
  }
  println(collectedList)
  println()

  println("flatten / flatMap")
  val expandedList1: List[List[Int]] = list.map(el => List.fill(el)(el))
  val flattenedList1: List[Int] = expandedList1.flatten
  println(flattenedList1)

  val flattenedList2 = list.flatMap(el => List.fill(el)(el))
  println(flattenedList2)
  println()

  println("head / init / last / tail")
  println(list.head)
  println(list.init)
  println(list.last)
  println(list.tail)
  println()

  // Несколько методов, которые возвращают итератор
  println("iterator utilities")
  // Разбивает список на списки длины n
  println(list.grouped(2).toList)
  // Возвращает списки, полученные с помощью скользящего окна длины n
  println(list.sliding(3).toList)
  println()

  println("groupBy")
  // Возвращает ассоциативный массив, где ключами выступают значения, возвращаемые функцией
  println(list.groupBy(_ % 2))
}

object listCheck extends App {
  val list = List(0, 1, 2, 3, 4, 5, 6)

  println("isEmpty")
  println(list.isEmpty)
  println(Nil.isEmpty)
  println()

  println("nonEmpty")
  println(list.nonEmpty)
  println(Nil.nonEmpty)
  println()

  println("size")
  println(list.size)
  println()

  println("contains")
  println(list.contains(3))
  println(list.contains(451))
  println()

  println("forall")
  println(list.forall(_ < 0))
  println(list.forall(_ < 3))
  println(list.forall(_ < 10))
  println()

  println("exists")
  println(list.exists(_ < 0))
  println(list.exists(_ < 3))
  println(list.exists(_ < 10))
}

object listFold extends App {
  val list = List(0, 1, 2, 3, 4, 5, 6)

  val reducedInts = list.reduce((a, b) => 10 * a + b)
  println(reducedInts)

  val foldedInts1 = list.foldLeft("")((acc, item) => acc + item.toString * item)
  println(foldedInts1)

  val foldedInts2 = list.foldRight("Nil")((item, acc) => item + " :: " + acc)
  println(foldedInts2)

  val mapReducedList = list.groupMapReduce(_ % 2)(_.toString)(_ + _)
  println(mapReducedList)
}

object listComprehension extends App {
  val ints = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c')

  val pairs1 =
    for {
      int <- ints
      char <- chars
    } yield (int, char)

  val pairs2 =
    ints.flatMap(int =>
      chars.map(char =>
        (int, char)
      )
    )

  println(pairs1)
  println(pairs2)

  println(pairs1 == pairs2)


  val xs = List(5, 4, 9, 1, 2)
  val ys = List(8, 3, 5, 0, 4, 6)
  val zs = List(15, 0, 12)

  val numbers1 =
    for {
      x <- xs
      y <- ys
      z <- zs if z == x * y
    } yield (x, y)

  val numbers2 =
    xs.flatMap(x =>
      ys.flatMap(y =>
        zs.withFilter(z => z == x * y).map(z => (x, y))
      )
    )

  println(numbers1)
  println(numbers2)
  println(numbers1 == numbers2)


  val intsTo = for {
    i <- 1 to 10
  } yield i * 2

  println(intsTo)

  val intsUntil = for {
    i <- 1 until 10
  } yield i * 2

  println(intsUntil)
}
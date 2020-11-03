package homeworks.homework2

object task4 extends App {

  sealed trait BinaryTree[+A] {
    // Реализуйте методы insert и contains для бинарного дерева со значениями произвольного типа,
    // используя тайпкласс scala.Ordering.
    // Сигнатуры методов insert/contains или классов можно (и нужно) модифицировать
    // для работы с неявными параметрами
    def insert[B >: A](newValue: B): BinaryTree[B] = ???

    def contains[B >: A](newValue: B): Boolean = ???

    def dump(depth: Int = 0): Unit =
      this match {
        case Branch(value, left, right) =>
          right.dump(depth + 1)
          println(" " * depth + value)
          left.dump(depth + 1)
        case _ =>
      }
  }

  final case class Branch[A](value: A, left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A]

  case object Leaf extends BinaryTree[Nothing]

  Leaf
    .insert("DEF")
    .insert("ABC")
    .insert("IJK")
    .insert("FGH")
    .insert("LMN")
    .dump()

  //   LMN
  //  IJK
  //   FGH
  // DEF
  //  ABC
}

package seminars.seminar3

object slide3 extends App {

  trait Book {
    def name: String
  }

  case class Encyclopedia(name: String, volume: Int) extends Book

  case class Novel(name: String) extends Book

  // В этом примере Stack ковариантен по type-параметру A.
  // Это значит, что если некоторый тип X - подтип (наследник) Y, то и Stack[X] - подтип Stack[Y]
  sealed trait Stack[+A] {
    def push[B >: A](item: B): Stack[B] = StackNode[B](item, this)

    def pop: Option[(A, Stack[A])] =
      this match {
        case StackNode(head, tail) => Some((head, tail))
        case EmptyStack => None
      }
  }

  final case class StackNode[+A](head: A, tail: Stack[A]) extends Stack[A]

  final case object EmptyStack extends Stack[Nothing]

  val stackOfBooks =
    EmptyStack
      .push(Novel("Crime and Punishment"))
      .push(Encyclopedia("Britannica", 5))
      .push(Novel("Lord of the rings"))

}

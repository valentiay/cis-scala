package seminars.seminar3

object slide1 extends App {

  sealed trait Stack[A] {
    def push(item: A): Stack[A] = StackNode(item, this)

    def pop: Option[(A, Stack[A])] =
      this match {
        case StackNode(head, tail) => Some((head, tail))
        case EmptyStack() => None
      }
  }

  final case class StackNode[A](head: A, tail: Stack[A]) extends Stack[A]

  final case class EmptyStack[A]() extends Stack[A]

  object Stack {
    def empty[A]: EmptyStack[A] = EmptyStack[A]()
  }

  val collections =
    Stack
      .empty
      .push("Vector")
      .push("List")
      .push("Seq")
      .push("Set")

  println(collections)

  println()

  val tuple: (String, Stack[String]) = collections.pop.get
  println(tuple)
  println(tuple._1)
  println(tuple._2)
  val (head, tail) = tuple
  println(head)
  println(tail)
}

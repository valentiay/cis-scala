package seminars.seminar3

object slide2 extends App {

  trait Book {
    def name: String
  }

  case class Encyclopedia(name: String, volume: Int) extends Book

  case class Novel(name: String) extends Book

  // Ограничение на тип A сверху: любой A должен наследовать интерфейс Book
  sealed trait StackOfBooks[A <: Book] {
    def push(item: A): StackOfBooks[A] = StackNode(item, this)

    def catalog: String =
      this match {
        case StackNode(head, tail) => head.name + "\n" + tail.catalog
        case EmptyStack() => ""
      }

    def pop: Option[(A, StackOfBooks[A])] =
      this match {
        case StackNode(head, tail) => Some((head, tail))
        case EmptyStack() => None
      }
  }

  final case class StackNode[A <: Book](head: A, tail: StackOfBooks[A]) extends StackOfBooks[A]

  final case class EmptyStack[A <: Book]() extends StackOfBooks[A]

  object StackOfBooks {
    def empty[A <: Book]: EmptyStack[A] = EmptyStack[A]()
  }

  // Не скомплируется, так как не подходит тип
  //val collections =
  //  Stack
  //    .empty
  //    .push("Vector")

  val stackOfBooks =
    StackOfBooks
      .empty[Book] // Без указания типа выведется тип Novel, а не Book. В таком случае код ниже не скомпилируется
      .push(Novel("Crime and Punishment"))
      .push(Encyclopedia("Britannica", 5))
      .push(Novel("Lord of the rings"))

  println(stackOfBooks.catalog)
}

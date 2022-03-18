package seminars.seminar2

import BinaryTree._

// Модификатор sealed означает, что наследник этого интерфейса может быть определен только в этом файле
sealed trait BinaryTree {
  def insert(newValue: Int): BinaryTree =
    this match {
      case Branch(value, left, right) if newValue <= value =>
        Branch(value, left.insert(newValue), right)
      case Branch(value, left, right) if value < newValue =>
        Branch(value, left, right.insert(newValue))
      case Leaf =>
        Branch(newValue, Leaf, Leaf)
    }

  def contains(newValue: Int): Boolean =
    this match {
      case Branch(value, left, _) if newValue < value =>
        left.contains(newValue)
      case Branch(value, _, right) if value < newValue =>
        right.contains(newValue)
      case Branch(value, _, _) if value == newValue =>
        true
      case Leaf =>
        false
    }

  def dump(depth: Int = 0): Unit =
    this match {
      case Branch(value, left, right) =>
        right.dump(depth + 1)
        println(" " * depth + value)
        left.dump(depth + 1)
      case _ =>
    }
}

object BinaryTree {

  final case class Branch(value: Int, left: BinaryTree, right: BinaryTree) extends BinaryTree

  case object Leaf extends BinaryTree

}

object slide6 extends App {
  val tree =
    Leaf
      .insert(5)
      .insert(4)
      .insert(8)
      .insert(10)
      .insert(6)

  tree.dump()

  // Из каждого вызова .insert возвращается новый экземпляр трейта BinaryTree.
  // Внутренняя структура частично сохраняется. Меняется только обновленное поддерево.
  val tree1 = Leaf.insert(5)
  val tree2 = tree1.insert(4)
  val tree3 = tree2.insert(8)
  println(tree1)
  println(tree2)
  println(tree3)

}

package seminars.seminar2

import BinaryTree._

import scala.util.Random

// Модификатор sealed означает, что наследник этого интерфейса может быть определен только в этом файле
sealed trait BinaryTree {
  def insert(newValue: Int): BinaryTree =
    this match {
      case Branch(value, left, right) if newValue <= value => Branch(value, left.insert(newValue), right)
      case Branch(value, left, right) if value < newValue => Branch(value, left, right.insert(newValue))
      case Leaf => Branch(newValue, Leaf, Leaf)
    }

  def contains(newValue: Int): Boolean =
    this match {
      case Branch(value, left, _) if newValue < value => left.contains(newValue)
      case Branch(value, _, right) if value < newValue => right.contains(newValue)
      case Branch(value, _, _) if value == newValue => true
      case Leaf => false
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

object slide5 extends App {
  val tree =
    List.fill(10)(Random.nextInt(10))
      .foldLeft[BinaryTree](Leaf)((tree, item) => tree.insert(item))
  tree.dump()

  for (i <- 0 to 9) println(s"Contains $i: ${tree.contains(i)}")
}

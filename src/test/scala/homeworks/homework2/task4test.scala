package homeworks.homework2

import homeworks.homework2.task4.{BinaryTree, Leaf}
import homeworks.homework2.task4test.OogaBooga
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class task4test extends AnyWordSpec with Matchers {
  "BinaryTree.insert" should {
    "work for ints" in {
      val tree = List.fill(10000)(Random.nextInt).foldLeft[BinaryTree[Int]](Leaf)((tree, int) => tree.insert(int))

      def check(tree: BinaryTree[Int], parent: Int, isLeft: Boolean): Boolean =
        tree match {
          case task4.Branch(value, left, right) =>
            (isLeft && value <= parent || !isLeft && parent <= value) &&
              check(left, value, isLeft = true) &&
              check(right, value, isLeft = false)
          case task4.Leaf =>
            true
        }

      tree match {
        case task4.Branch(value, left, right) =>
          check(left, value, isLeft = true) shouldBe true
          check(right, value, isLeft = false) shouldBe true
        case task4.Leaf =>
          fail
      }
    }

    "work for strings" in {
      val tree =
        List
          .fill(10000)(task4test.genString())
          .foldLeft[BinaryTree[String]](Leaf)((tree, string) => tree.insert(string))

      def check(tree: BinaryTree[String], parent: String, isLeft: Boolean): Boolean =
        tree match {
          case task4.Branch(value, left, right) =>
            (isLeft && value <= parent || !isLeft && parent <= value) &&
              check(left, value, isLeft = true) &&
              check(right, value, isLeft = false)
          case task4.Leaf =>
            true
        }

      tree match {
        case task4.Branch(value, left, right) =>
          check(left, value, isLeft = true) shouldBe true
          check(right, value, isLeft = false) shouldBe true
        case task4.Leaf =>
          fail
      }
    }

    "work for OogaBooga" in {
      val tree =
        List
          .fill(10000)(task4test.genOogaBooga())
          .foldLeft[BinaryTree[OogaBooga]](Leaf)((tree, oogaBooga) => tree.insert(oogaBooga))

      def check(tree: BinaryTree[OogaBooga], parent: OogaBooga, isLeft: Boolean): Boolean =
        tree match {
          case task4.Branch(value, left, right) =>
            (isLeft && value.hashCode <= parent.hashCode || !isLeft && parent.hashCode <= value.hashCode) &&
              check(left, value, isLeft = true) &&
              check(right, value, isLeft = false)
          case task4.Leaf =>
            true
        }

      tree match {
        case task4.Branch(value, left, right) =>
          check(left, value, isLeft = true) shouldBe true
          check(right, value, isLeft = false) shouldBe true
        case task4.Leaf =>
          fail
      }
    }
  }

  "BinaryTree.contains" should {
    "work for ints" in {
      val valuesInTree = Set.fill(10000)(Random.nextInt)
      val valuesNotInTree = Set.fill(20000)(Random.nextInt) -- valuesInTree
      val tree = valuesInTree.foldLeft[BinaryTree[Int]](Leaf)((tree, int) => tree.insert(int))
      valuesInTree.forall(tree.contains) shouldBe true
      valuesNotInTree.exists(tree.contains) shouldBe false
    }

    "work for strings" in {
      val valuesInTree = Set.fill(10000)(task4test.genString())
      val valuesNotInTree = Set.fill(20000)(task4test.genString()) -- valuesInTree
      val tree = valuesInTree.foldLeft[BinaryTree[String]](Leaf)((tree, int) => tree.insert(int))
      valuesInTree.forall(tree.contains) shouldBe true
      valuesNotInTree.exists(tree.contains) shouldBe false
    }

    "work for OogaBooga" in {
      val valuesInTree = Set.fill(10000)(task4test.genOogaBooga())
      val valuesNotInTree = Set.fill(20000)(task4test.genOogaBooga()) -- valuesInTree
      val tree = valuesInTree.foldLeft[BinaryTree[OogaBooga]](Leaf)((tree, int) => tree.insert(int))
      valuesInTree.forall(tree.contains) shouldBe true
      valuesNotInTree.exists(tree.contains) shouldBe false
    }
  }
}

object task4test {

  final case class OogaBooga(int: Int, string: String)

  object OogaBooga {
    implicit val oogaBoogaOrdering: Ordering[OogaBooga] =
      (x: OogaBooga, y: OogaBooga) => x.hashCode().compareTo(y.hashCode())
  }

  def genString(): String = Random.alphanumeric.take(Random.nextInt(100)).mkString

  def genOogaBooga(): OogaBooga =
    OogaBooga(
      Random.nextInt(),
      Random.alphanumeric.take(Random.nextInt(100)).mkString
    )
}
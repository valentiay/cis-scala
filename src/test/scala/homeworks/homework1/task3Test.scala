package homeworks.homework1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class task3Test extends AnyWordSpec with Matchers {

  val items1: List[Int] =
    List(5, 4, 8, 10, 6)

  val tree1: BinaryTree =
    items1.foldLeft[BinaryTree](Leaf)(_ insert _)

  val items2: List[Int] =
    List.fill(10)(Random.nextInt(100))

  val tree2: BinaryTree =
    items2.foldLeft[BinaryTree](Leaf)(_ insert _)

  val items3: List[Int] =
    List.fill(100)(Random.nextInt(100))

  val tree3: BinaryTree =
    items3.foldLeft[BinaryTree](Leaf)(_ insert _)


  "BinaryTree.forall" should {
    "return true if predicate is true for every item" in {
      tree1.forall(_ => true) shouldBe true
      tree2.forall(_ => true) shouldBe true
      tree3.forall(_ => true) shouldBe true
    }

    "return false if predicate is false for every item" in {
      tree1.forall(_ => false) shouldBe false
      tree2.forall(_ => false) shouldBe false
      tree3.forall(_ => false) shouldBe false
    }

    "return false if predicate is false for some items" in {
      tree1.forall(item => items1.headOption.exists(_ != item)) shouldBe false
      tree2.forall(item => items2.headOption.exists(_ != item)) shouldBe false
      tree3.forall(item => items3.headOption.exists(_ != item)) shouldBe false
    }

    "correspond to List.forall for tree items" in {
      tree1.forall(_ % 20 == 5) shouldBe items1.forall(_ % 20 == 5)
      tree2.forall(_ % 20 == 5) shouldBe items2.forall(_ % 20 == 5)
      tree3.forall(_ % 20 == 5) shouldBe items3.forall(_ % 20 == 5)
    }
  }

  "BinaryTree.exists" should {
    "return true if predicate is true for every item" in {
      tree1.exists(_ => true) shouldBe true
      tree2.exists(_ => true) shouldBe true
      tree3.exists(_ => true) shouldBe true
    }

    "return false if predicate is false for every item" in {
      tree1.exists(_ => false) shouldBe false
      tree2.exists(_ => false) shouldBe false
      tree3.exists(_ => false) shouldBe false
    }

    "return true if predicate is true for some items" in {
      tree1.exists(item => items1.headOption.contains(item)) shouldBe true
      tree2.exists(item => items2.headOption.contains(item)) shouldBe true
      tree3.exists(item => items3.headOption.contains(item)) shouldBe true
    }

    "correspond to List.exists for tree items" in {
      tree1.exists(_ % 20 == 5) shouldBe items1.exists(_ % 20 == 5)
      tree2.exists(_ % 20 == 5) shouldBe items2.exists(_ % 20 == 5)
      tree3.exists(_ % 20 == 5) shouldBe items3.exists(_ % 20 == 5)
    }
  }

  "BinaryTree.size" should {
    "correspond to List.size for tree items" in {
      tree1.size shouldBe items1.size
      tree2.size shouldBe items2.size
      tree3.size shouldBe items3.size
    }
  }

  "BinaryTree.take" should {
    "take tree of correct size" in {
      for {
        tree <- List(tree1, tree2, tree3)
        n <- List(0, 1, 5, 10, 25, 50, 75, 100)
      } tree.take(n).size shouldBe n.min(tree.size)
    }

    "contain first elements of tree" in {
      for {
        (tree, items) <- List(tree1, tree2, tree3).zip(List(items1.sorted, items2.sorted, items3.sorted))
        n <- List(0, 1, 5, 10, 25, 50, 75, 100)
        treePrefix = tree.take(n)
      } items.take(n).forall(treePrefix.contains) shouldBe true
    }
  }

  "BinaryTree.foldLeft" should {
    "correspond to sorted List foldLeft" in {
      for {
        (tree, items) <- List(tree1, tree2, tree3).zip(List(items1.sorted, items2.sorted, items3.sorted))
      } {
        tree.foldLeft(0)(_ + _) shouldBe items.sorted.sum
        tree.foldLeft(List.empty[Int])((list, item) => item :: list) shouldBe items.reverse
      }
    }
  }
}

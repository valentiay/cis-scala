package homeworks.homework2

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class task2test extends AnyWordSpec with Matchers {
  "traverse" should {
    "return None if list of Nones was passed" in {
      for (n <- 1 to 1000) task2.traverse(List.fill(n)(None)) shouldBe None
    }

    "return None if list of both Nones and Somes was passed" in {
      val init = Some(0) :: None :: Nil
      for (n <- 1 to 1000)
        task2.traverse(init ::: List.fill(n)(Some(Random.nextInt(50)).filterNot(_ == 0))) shouldBe None
    }

    "return Some of list if list of Somes was passed" in {
      for (n <- 1 to 1000) {
        val list = List.fill(n)(Random.nextInt(50))
        task2.traverse(list.map(x => Some(x))) shouldBe Some(list)
      }
    }
  }

  "splitEither" should {
    "return (Nil, List(...)) if list of Rights was passed" in {
      for (n <- 1 to 1000) {
        val list = List.fill(n)(Random.nextInt(50))
        val result = task2.splitEither(list.map(Right.apply))
        result._1 shouldBe Nil
        result._2 should contain theSameElementsAs list
      }
    }

    "return (List(...), Nil) if list of Lefts was passed" in {
      for (n <- 1 to 1000) {
        val list = List.fill(n)(Random.nextInt(50))
        val result = task2.splitEither(list.map(Left.apply))
        result._1 should contain theSameElementsAs list
        result._2 shouldBe Nil
      }
    }

    "return (List(...), List(...)) if list of both Lefts and Rights was passed" in {
      for (n <- 1 to 1000) {
        val rights = List.fill(n)(Random.nextInt(50))
        val lefts = List.fill(n)(Random.alphanumeric.take(10).mkString)
        val list = Random.shuffle(rights.map(Right.apply) ::: lefts.map(Left.apply))
        val result = task2.splitEither(list)
        result._1 should contain theSameElementsAs lefts
        result._2 should contain theSameElementsAs rights
      }
    }
  }

  "validate" should {
    "return Right(List(...)) if list of Rights was passed" in {
      for (n <- 1 to 1000) {
        val list = List.fill(n)(Random.nextInt(50))
        val result = task2.validate(list.map(Right.apply))
        result match {
          case Left(values) => fail(s"Result should be right, got Left($values)")
          case Right(values) => values should contain theSameElementsAs list
        }
      }
    }

    "return Left(List(...)) if list of Lefts was passed" in {
      for (n <- 1 to 1000) {
        val list = List.fill(n)(Random.nextInt(50))
        val result = task2.validate(list.map(Left.apply))
        result match {
          case Left(values) => values should contain theSameElementsAs list
          case Right(values) => fail(s"Result should be left, got Right($values)")
        }
      }
    }

    "return Left(List(...)) if list of both Lefts and Rights was passed" in {
      for (n <- 1 to 1000) {
        val rights = List.fill(n)(Random.nextInt(50))
        val lefts = List.fill(n)(Random.alphanumeric.take(10).mkString)
        val list = Random.shuffle(rights.map(Right.apply) ::: lefts.map(Left.apply))
        val result = task2.validate(list)
        result match {
          case Left(values) => values should contain theSameElementsAs lefts
          case Right(values) => fail(s"Result should be left, got Right($values)")
        }
      }
    }
  }
}

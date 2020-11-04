package homeworks.homework2

import cats.data.NonEmptyList
import cats.instances.int._
import cats.instances.string._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class task3test extends AnyWordSpec with Matchers {
  "combineErrors" should {
    "return right if right was passed" in {
      for (_ <- 1 to 100) {
        val randomInt = Random.nextInt(10000)
        task3.combineErrors[Int, Int](Right(randomInt)) shouldBe Right(randomInt)
        val randomString = Random.alphanumeric.take(Random.nextInt(1000)).mkString
        task3.combineErrors[Int, String](Right(randomString)) shouldBe Right(randomString)
      }
    }

    "return combination if left of list was passed" in {
      for (n <- 1 to 100) {
        val randomInts = NonEmptyList.fromListUnsafe(List.fill(n)(Random.nextInt(10000)))
        task3.combineErrors(Left(randomInts)) shouldBe Left(randomInts.toList.sum)
        val randomStrings = NonEmptyList.fromListUnsafe(List.fill(n)(Random.alphanumeric.take(Random.nextInt(1000)).mkString))
        task3.combineErrors(Left(randomStrings)) shouldBe Left(randomStrings.toList.mkString)
      }
    }
  }

  "mergeMaps" should {
    "merge maps int -> int" in {
      for (_ <- 1 to 100) {
        val keys = (1 to 100).toList.map(_ -> Random.nextInt(3)).groupBy(_._2)
        val commonKeys = keys.getOrElse(0, Nil).map(_._1)
        val leftKeys = keys.getOrElse(1, Nil).map(_._1)
        val rightKeys = keys.getOrElse(2, Nil).map(_._1)
        val leftCommon = commonKeys.map(_ -> Random.nextInt(10000))
        val rightCommon = commonKeys.map(_ -> Random.nextInt(10000))
        val leftSpecific = leftKeys.map(_ -> Random.nextInt(10000))
        val rightSpecific = rightKeys.map(_ -> Random.nextInt(10000))
        task3.mergeMaps(Map.from(leftCommon ++ leftSpecific), Map.from(rightCommon ++ rightSpecific)) shouldBe
          Map.from(leftCommon.zip(rightCommon).map { case ((k1, v1), (k2, v2)) => k1 -> (v1 + v2) } ++ leftSpecific ++ rightSpecific)
      }
    }

    "merge maps int -> string" in {
      for (_ <- 1 to 100) {
        val keys = (1 to 100).toList.map(_ -> Random.nextInt(3)).groupBy(_._2)
        val commonKeys = keys.getOrElse(0, Nil).map(_._1)
        val leftKeys = keys.getOrElse(1, Nil).map(_._1)
        val rightKeys = keys.getOrElse(2, Nil).map(_._1)
        val leftCommon = commonKeys.map(_ -> Random.alphanumeric.take(100).mkString)
        val rightCommon = commonKeys.map(_ -> Random.alphanumeric.take(100).mkString)
        val leftSpecific = leftKeys.map(_ -> Random.alphanumeric.take(100).mkString)
        val rightSpecific = rightKeys.map(_ -> Random.alphanumeric.take(100).mkString)
        task3.mergeMaps(Map.from(leftCommon ++ leftSpecific), Map.from(rightCommon ++ rightSpecific)) shouldBe
          Map.from(leftCommon.zip(rightCommon).map { case ((k1, v1), (k2, v2)) => k1 -> (v1 + v2) } ++ leftSpecific ++ rightSpecific)
      }
    }
  }
}

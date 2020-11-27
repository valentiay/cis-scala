package homeworks.homework3

import homeworks.homework3.task2._
import org.scalatest.wordspec.AsyncWordSpec
import cats.implicits._
import org.scalatest.matchers.should.Matchers

class task2test extends AsyncWordSpec with Matchers {
  "getCities" should {

    val codes =
      List("MOW", "LED", "OVB", "SVX", "KZN", "GOJ", "CEK", "KUF", "OMS", "ROV")

    "return correct cities" in {
      codes
        .permutations
        .flatMap(_.combinations(4))
        .take(1000)
        .toList
        .traverse { subset =>
          for {
            taskCities <- getCities(subset)
            city1 <- getCity(subset(0))
            city2 <- getCity(subset(1))
            city3 <- getCity(subset(2))
            city4 <- getCity(subset(3))
            correctCities = List(city1, city2, city3, city4)
          } yield taskCities should contain theSameElementsAs(correctCities)
        }
        .as(succeed)
        .unsafeToFuture()
    }

    "return cities in order of priority" in {
      codes
        .permutations
        .flatMap(_.combinations(4))
        .take(1000)
        .toList
        .traverse(getCities)
        .map{
          _.forall(_.sliding(2).forall{case List(a, b) => a.priority >= b.priority}) shouldBe true
        }
        .unsafeToFuture()
    }

    "fail on first error" in {
      getCities(codes ::: "noway" :: codes)
        .attempt
        .map(_ shouldBe Left(Error(s"No such city: noway")))
        .unsafeToFuture()
    }
  }
}

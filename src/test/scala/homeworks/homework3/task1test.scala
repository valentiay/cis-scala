package homeworks.homework3

import cats.effect.IO
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

import scala.util.Random

class task1test extends AsyncWordSpec with Matchers {
  "map3" should {
    val randomString = IO(Random.alphanumeric.take(Random.nextInt(4) + 1).mkString)

    "work for (a: Int, b: Int, c: Int) => a * b * c" in {
      val f = (a: Int, b: Int, c: Int) => a * b * c
      (for {
        a <- IO(Random.nextInt)
        b <- IO(Random.nextInt)
        c <- IO(Random.nextInt)
        res <- task1.map3(IO.pure(a), IO.pure(b), IO.pure(c))(f)
      } yield res shouldBe f(a, b, c)).unsafeToFuture()
    }

    "work for (a: String, b: String, c: String) => a + b + c" in {
      val f = (a: String, b: String, c: String) => a + b + c
      (for {
        a <- randomString
        b <- randomString
        c <- randomString
        res <- task1.map3(IO.pure(a), IO.pure(b), IO.pure(c))(f)
      } yield res shouldBe f(a, b, c)).unsafeToFuture()
    }

    "work for (a: String, b: Int, c: Double) => a * (c * b).abs.ceil.toInt" in {
      val f = (a: String, b: Int, c: Double) => a * (c * b).abs.ceil.toInt
      (for {
        a <- randomString
        b <- IO(Random.nextInt(501) - 250)
        c <- IO(Random.nextDouble)
        res <- task1.map3(IO.pure(a), IO.pure(b), IO.pure(c))(f)
      } yield res shouldBe f(a, b, c)).unsafeToFuture()
    }
  }
}

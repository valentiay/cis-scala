package seminars.seminar11.math

import cats.effect.IO
import io.finch._

object MathModule extends Endpoint.Module[IO] {
  val sum: Endpoint[IO, Int] =
    get(
      path("sum") ::
        param[Int]("x") ::
        param[Int]("y")
    )(MathService.sum _)

  val product: Endpoint[IO, Int] =
    get(
      path("product") ::
        param[Int]("x") ::
        param[Int]("y")
    )(MathService.product _)

  val difference: Endpoint[IO, Int] =
    get(
      path("difference") ::
        param[Int]("x") ::
        param[Int]("y")
    )(MathService.difference _)

  val division: Endpoint[IO, Int] =
    get(
      path("division") ::
        param[Int]("x") ::
        param[Int]("y").shouldNot("be zero")(_ == 0)
    )(MathService.division _)

  val api = path("math") :: (sum :+: product :+: difference :+: division)
}

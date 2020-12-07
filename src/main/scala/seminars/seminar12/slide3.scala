package seminars.seminar12

import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, HCursor}

object slide3 extends App {

  sealed trait Bar

  object Bar {
    final case class Baz(int: Int, string: String) extends Bar
    final case class Qux(long: Long) extends Bar

    implicit val bazDecoder: Decoder[Baz] =
      new Decoder[Baz] {
        def apply(c: HCursor): Result[Baz] =
          for {
            int <- c.downField("int").as[Int]
            string <- c.downField("string").as[String]
          } yield Baz(int, string)
      }

    implicit val quxDecoder: Decoder[Qux] =
      new Decoder[Qux] {
        def apply(c: HCursor): Result[Qux] =
          for {
            long <- c.downField("long").as[Long]
          } yield Qux(long)
      }

    implicit val barDecoder: Decoder[Bar] =
      new Decoder[Bar] {
        def apply(c: HCursor): Result[Bar] =
          for {
            typ <- c.downField("type").as[String]
            bar <- typ match {
              case "baz" => bazDecoder.apply(c)
              case "qux" => quxDecoder.apply(c)
              case unknown => Left(DecodingFailure(s"Unknown type: $unknown", c.history))
            }
          } yield bar
      }
  }

  final case class Foo(boolean: Boolean, bar: Option[Bar], doubles: List[Double], maybeChar: Option[Char])

  object Foo {
    implicit val fooDecoder: Decoder[Foo] =
      new Decoder[Foo] {
        def apply(c: HCursor): Result[Foo] =
          for {
            boolean <- c.downField("boolean").as[Boolean]
            bar <- c.downField("bar").as[Option[Bar]]
            doubles <- c.downField("doubles").as[List[Double]]
            maybeChar <- c.downField("maybeChar").as[Option[Char]]
          } yield Foo(boolean, bar, doubles, maybeChar)
      }
  }

  val correct1 =
    io.circe.parser.decode[Foo](
      """{
        |  "boolean" : true,
        |  "bar" : {
        |    "int" : 1,
        |    "string" : "abc",
        |    "type" : "baz"
        |  },
        |  "doubles" : [
        |    1.2,
        |    1.23,
        |    2.48
        |  ],
        |  "maybeChar" : null
        |}
        |""".stripMargin
    )

  val correct2 =
    io.circe.parser.decode[Foo](
      """
        |{
        |  "boolean" : true,
        |  "bar" : null,
        |  "doubles" : [
        |    1.2,
        |    1.23,
        |    2.48
        |  ],
        |  "maybeChar" : "x"
        |}
        |""".stripMargin
    )

  val incorrect1 =
    io.circe.parser.decode[Foo](
      """
        |{
        |  "boolean" : true,
        |  "bar" : null,
        |  "doubles" : [
        |    1.2,
        |    1.23,
        |    "NaN"
        |  ],
        |  "maybeChar" : "x"
        |}
        |""".stripMargin
    )

  val incorrect2 =
    io.circe.parser.decode[Foo](
      """{
        |  "boolean" : true,
        |  "bar" : {
        |    "type" : "wrong"
        |  },
        |  "doubles" : [
        |    1.2,
        |    1.23,
        |    2.48
        |  ],
        |  "maybeChar" : null
        |}
        |""".stripMargin
    )

  println(correct1)
  println(correct2)
  println(incorrect1)
  println(incorrect2)

}

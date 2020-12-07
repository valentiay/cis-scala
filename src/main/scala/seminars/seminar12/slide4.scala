package seminars.seminar12

import io.circe.syntax._

object slide4 extends App {

  import data._
  import Bar._

  val foo1 = Foo(boolean = true, bar = Some(Baz(1, "abc")), doubles = List(1.2, 1.23, 2.48), maybeChar = None)
  val foo2 = Foo(boolean = true, bar = None, doubles = List(1.2, 1.23, 2.48), maybeChar = Some('x'))

  val correct1 = foo1.asJson.spaces2
  val correct2 = foo2.asJson.spaces2
  println(correct1)
  println()
  println(correct2)

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

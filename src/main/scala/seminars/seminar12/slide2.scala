package seminars.seminar12

import io.circe.{Encoder, JsonObject}
import io.circe.syntax._

object slide2 extends App {

  sealed trait Bar

  object Bar {
    final case class Baz(int: Int, string: String) extends Bar
    final case class Qux(long: Long) extends Bar

    implicit val bazEncoder: Encoder.AsObject[Baz] =
      new Encoder.AsObject[Baz] {
        def encodeObject(baz: Baz): JsonObject =
          JsonObject(
            "int" -> baz.int.asJson,
            "string" -> baz.string.asJson,
          )
      }

    implicit val quxEncoder: Encoder.AsObject[Qux] =
      new Encoder.AsObject[Qux] {
        def encodeObject(qux: Qux): JsonObject =
          JsonObject("long" -> qux.long.asJson)
      }

    implicit val barEncoder: Encoder.AsObject[Bar] =
      new Encoder.AsObject[Bar] {
        def encodeObject(bar: Bar): JsonObject =
          bar match {
            case baz: Baz => baz.asJsonObject.add("type", "baz".asJson)
            case qux: Qux => qux.asJsonObject.add("type", "qux".asJson)
          }
      }
  }

  import Bar._

  final case class Foo(boolean: Boolean, bar: Option[Bar], doubles: List[Double], maybeChar: Option[Char])

  object Foo {
    implicit val fooEncoder: Encoder[Foo] =
      new Encoder.AsObject[Foo] {
        def encodeObject(foo: Foo): JsonObject =
          JsonObject(
            "boolean" -> foo.boolean.asJson,
            "bar" -> foo.bar.asJson,
            "doubles" -> foo.doubles.asJson,
            "maybeChar" -> foo.maybeChar.asJson,
          )
      }
  }


  val foo1 = Foo(boolean = true, bar = Some(Baz(1, "abc")), doubles = List(1.2, 1.23, 2.48), maybeChar = None)
  val foo2 = Foo(boolean = true, bar = None, doubles = List(1.2, 1.23, 2.48), maybeChar = Some('x'))

  println(foo1.asJson.spaces2)
  println()
  println(foo2.asJson.spaces2)
}

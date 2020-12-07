package seminars.seminar12

import io.circe.Json
import jsons._

object jsons {
  val string: Json = Json.fromString("string")
  val number: Json = Json.fromInt(1)
  val boolean: Json = Json.True
  val nul: Json = Json.Null
  val array: Json = Json.arr(string, number, boolean, nul)
  val obj: Json =
    Json.obj(
      "foo" -> string,
      "bar" -> number,
      "baz" -> boolean,
      "qux" -> nul,
      "quux" -> array,
    )
  val otherObj: Json =
    Json.obj(
      "a" -> Json.fromString("aaa"),
      "b" -> Json.fromString("bbb"),
      "c" -> Json.fromString("ccc"),
      "d" -> Json.fromString("ddd"),
    )
}

object types extends App {
  println(string)
  println(number)
  println(boolean)
  println(nul)
  println(array)
  println(obj)
}

object printing extends App {
  println(obj.noSpaces)
  println()
  println(obj.spaces2)
  println()
  println(obj.spaces2SortKeys)
  println()
  println(obj.dropNullValues.spaces2)
}

object modifying extends App {
  val object1 = obj.asObject.get
  val object2 = otherObj.asObject.get

  println(
    object1.deepMerge(object2)
  )
  println()

  println(
    object1.add("New key", Json.fromString("New value"))
  )
  println()

  println(
    object1.remove("quux")
  )
  println()
}
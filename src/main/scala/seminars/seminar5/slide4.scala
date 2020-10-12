package seminars.seminar5

object slide4 extends App {
  val pair: (Int, String) = 1 -> "one"

  val map = Map(1 -> "one", 2 -> "two")

  println(map(1))
  println(map.get(2))
  println(map.getOrElse(2, "default"))
  println(map.getOrElse(4, "default"))
  println()

  println(map + (3 -> "three"))
  println(map + (2 -> "three"))
  println(map.updated(5, "five"))
  println(map.updatedWith(2) {
    case Some(_) => None
    case None => Some("two")
  })
  println(map.keys)
}

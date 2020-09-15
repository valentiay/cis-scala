package seminars.seminar2

import users._

// Case-класс - особый тип классов, который обычно используется для представления данных.
// По умолчанию все его члены являются val, то есть неизменяемы и доступны извне.
// Члены case-классa можно сделать изменяемыми, но это считается плохой практикой.
//
// Для case-класса генерируются методы .toString (для читаемого текстового представления),
// .equals (для сравнения) и .copy (для копирования с изменением полей)


final case class Name(first: String, last: String) {
  def show: String = first + " " + last
}

final case class User(login: String, name: Name, accessLevel: Int)

object users {
  val user1 =
    User(
      login = "valentiay",
      name = Name(
        first = "Alexander",
        last = "Valentinov",
      ),
      accessLevel = 10,
    )

  val user2 =
    User("valentiay", Name("Alexander", "Valentinov"), 10)

  val user3 =
    User.apply("pupkin", Name.apply("Vasiliy", "Pupkin"), 1)
}

object toString extends App {
  println(user1)
  println(user2)
  println(user3)
  println(user1.login)
  println(user1.name)
}

object equals extends App {
  println(user1 == user2)
  println(user1 == user3)
  println(user2 == user3)
}

object copy extends App {
  println(user1)
  val name4 = user1.name.copy(last = "Pushkin")
  val user4 = user1.copy(accessLevel = 1, name = name4)
  println(user4)
  println(user1 == user4)
}

package seminars.seminar2

import animals._

trait Animal {
  def species: String
}

// Интерфейсы могут наследовать другие интерфейсы
trait Pet extends Animal {
  def name: String
}

trait Friend {
  def name: String
}

// Такая запись типа означает, что строка species может иметь только значение "Canis lupus"
final case class Wolf(species: "Canis lupus") extends Animal
// Класс может наследовать несколько интерфейсов. Первый указывается через extends, остальные примешиваются через with
final case class Cat(name: String, species: "Felis catus") extends Pet with Friend
final case class Parrot(name: String, species: "Ara ararauna") extends Pet

object animals {
  val wolf = Wolf("Canis lupus")
  val cat1 = Cat("Alice", "Felis catus")
  val cat2 = Cat("Bob", "Felis catus")
  val parrot = Parrot("Charles", "Ara ararauna")
}

object patternMatching extends App {
  // Pattern matching (сопоставление с образцом) - еще одна условная конструкция.
  // Ее часто используют для исполнения различной логики для разных реализаций интерфейса.
  def match1(animal: Animal): String =
    animal match {
      // Если animal - это Cat, то первый параметр должен быть равен "Alice", второй запишется в значение species
      case Cat("Alice", species) =>
        s"It's my friend Alice, $species"
      // Если animal - это Cat, то первый параметр будет записан в значение name, а второй будет проигнорирован
      case Cat(name, _) =>
        s"It's $name, a cat I don't know"
      // Если animal - это Wolf, то сработает эта ветка
      case Wolf(_) =>
        "It's a wolf!"
      // Если ничего не подошло, то будет выполнена эта ветка. В other будет записано знчение из animal
      case other =>
        s"Someone I did not expect: $other"
    }

  println(match1(wolf))
  println(match1(cat1))
  println(match1(cat2))
  println(match1(parrot))

  println()

  def match2(animal: Animal): String =
    animal match {
      // Если animal - это Pet, сработает эта ветка.
      // Т.к. Pet - это интерфейс, его нельзя распаковать, как case-класс и тип указывается через двоеточие
      case _: Pet => "A pet"
      case _ => "Not a pet"
    }

  println(match2(wolf))
  println(match2(cat1))
  println(match2(cat2))
  println(match2(parrot))

  println()

  def match3(animal: Animal): String =
    animal match {
      // Эта ветка сработает, если animal - это cat и выполняется условие под if (имя не длиннее трех символов)
      case Cat(name, _) if name.length <= 3 =>
        "It's a cat with a short name"
      case _ =>
        "It's someone else"
    }

  println(match3(wolf))
  println(match3(cat1))
  println(match3(cat2))
  println(match3(parrot))

  // TODO: при pattern matching можно распаковывать вложенные классы
}

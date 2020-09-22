package seminars.seminar3

object slide4 extends App {
  trait Animal {
    def name: String
  }

  case class Cat(name: String) extends Animal
  case class Dog(name: String) extends Animal

  // В этом примере Printer контрвариантен по type-параметру A.
  // Это значит, что если некоторый тип X - подтип (наследник) Y, то Printer[Y], наоборот, подтип Printer[X]
  trait Printer[-A] {
    def print(value: A): Unit
  }

  class AnimalPrinter extends Printer[Animal] {
    def print(animal: Animal): Unit =
      println("The animal's name is: " + animal.name)
  }

  class CatPrinter extends Printer[Cat] {
    def print(cat: Cat): Unit =
      println("The cat's name is: " + cat.name)
  }

  def printMyCat(printer: Printer[Cat], cat: Cat): Unit =
    printer.print(cat)

  val catPrinter: Printer[Cat] = new CatPrinter
  val animalPrinter: Printer[Animal] = new AnimalPrinter

  printMyCat(catPrinter, Cat("Boots"))
  printMyCat(animalPrinter, Cat("Boots"))
}

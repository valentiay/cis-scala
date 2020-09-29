package homeworks.homework1

object task5 extends App {
  /**
   * Реализуйте метод optionProduct, возвращающий произведение значений внутри двух Option.
   * Если оба значения определены, возвращается их произведение.
   * Если хотя бы одно значение не определено, возвращается None.
   */
  def optionProduct(option1: Option[Int], option2: Option[Int]): Option[Int] = ???

  println(optionProduct(Some(3), Some(5)))
  // Some(15)
  println(optionProduct(Some(6), None))
  // None
  println(optionProduct(None, Some(8)))
  // None
  println(optionProduct(None, None))
  // None
}

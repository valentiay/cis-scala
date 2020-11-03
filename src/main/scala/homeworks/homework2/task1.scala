package homeworks.homework2

object task1 extends App {
  lazy val orders: Map[Int, List[String]] =
    Map(
      1 -> List("Apples", "Cherries", "Bananas"),
      2 -> List("Milk", "Yogurt", "Butter", "Salt", "Beans"),
      3 -> List("Sugar", "Bulgur", "Coffee")
    )

  def getOrder(id: Int): List[String] =
    orders.getOrElse(id, Nil)

  // Отдел аналитики продуктового интернет-магазина хочет получать список наименований,
  // которые начинаются на заданную букву и присутствуют в заказах с определенными id.
  //
  // Метод `getOrder` возвращает список наименований в заказе по id.
  //
  // Реализуйте метод getItems, который возвращает все наименования из заказов из списка `ids`,
  // которые начинаются на букву `firstLetter`. Порядок не важен
  def getItems(ids: List[Int], firstLetter: Char): List[String] = ???

  println(getItems(List(1, 2, 3), 'B'))
  // List(Bananas, Butter, Beans, Bulgur)
  println(getItems(List(2, 3, 4), 'C'))
  // List(Coffee)
}

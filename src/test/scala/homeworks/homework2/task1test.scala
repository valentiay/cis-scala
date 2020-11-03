package homeworks.homework2

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class task1test extends AnyWordSpec with Matchers {
  val orderIds = task1.orders.keys.toList

  "getItems" should {
    "return only items staring on firstLetter" in {
      'A'.to('Z').foreach(char =>
        all(task1.getItems(orderIds, char)) should startWith(char.toString)
      )
    }

    "return only items contained in required orders" in {
      orderIds.foreach(orderId =>
        'A'.to('Z').flatMap(char => task1.getItems(List(orderId), char))
          should contain theSameElementsAs task1.orders(orderId)
      )
    }

    "not return non existing items" in {
      (1.to(50).toSet -- orderIds).foreach(orderId =>
        'A'.to('Z').flatMap(char => task1.getItems(List(orderId), char)) shouldBe Nil
      )
    }
  }
}

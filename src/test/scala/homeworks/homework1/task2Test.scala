package homeworks.homework1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class task2Test extends AnyWordSpec with Matchers {
  "isSafePayment for DebitCard" should {
    "return true for safe payments" in {
      val safePayment1 =
        DebitCard(Price(5, "EUR"), MasterCard)
      val safePayment2 =
        DebitCard(Price(50000, "RUB"), Visa)
      val safePayment3 =
        DebitCard(Price(1000000, "USD"), MasterCard)
      val safePayment4 =
        DebitCard(Price(11, "UAH"), Visa)

      task2.isSafePayment(safePayment1) shouldBe true
      task2.isSafePayment(safePayment2) shouldBe true
      task2.isSafePayment(safePayment3) shouldBe true
      task2.isSafePayment(safePayment4) shouldBe true
    }

    "return false for unsafe payments" in {
      val unsafePayment1 =
        DebitCard(Price(10, "EUR"), Mir)
      val unsafePayment2 =
        DebitCard(Price(10123, "RUB"), Mir)
      val unsafePayment3 =
        DebitCard(Price(100000, "GBP"), Maestro)
      val unsafePayment4 =
        DebitCard(Price(39234, "CNY"), Maestro)

      task2.isSafePayment(unsafePayment1) shouldBe false
      task2.isSafePayment(unsafePayment2) shouldBe false
      task2.isSafePayment(unsafePayment3) shouldBe false
      task2.isSafePayment(unsafePayment4) shouldBe false
    }
  }

  "isSafePayment for Cash" should {
    "return true for safe payments" in {
      val safePayment1 =
        Cash(Price(10, "RUB"), isLargeDenominations = false)
      val safePayment2 =
        Cash(Price(200, "RUB"), isLargeDenominations = false)
      val safePayment3 =
        Cash(Price(3000, "RUB"), isLargeDenominations = false)
      val safePayment4 =
        Cash(Price(4410, "RUB"), isLargeDenominations = false)

      task2.isSafePayment(safePayment1) shouldBe true
      task2.isSafePayment(safePayment2) shouldBe true
      task2.isSafePayment(safePayment3) shouldBe true
      task2.isSafePayment(safePayment4) shouldBe true
    }

    "return false for unsafe payments" in {
      val unsafePayment1 =
        Cash(Price(10000, "EUR"), isLargeDenominations = true)
      val unsafePayment2 =
        Cash(Price(100, "EUR"), isLargeDenominations = false)
      val unsafePayment3 =
        Cash(Price(6000, "RUB"), isLargeDenominations = false)
      val unsafePayment4 =
        Cash(Price(1000, "RUB"), isLargeDenominations = true)

      task2.isSafePayment(unsafePayment1) shouldBe false
      task2.isSafePayment(unsafePayment2) shouldBe false
      task2.isSafePayment(unsafePayment3) shouldBe false
      task2.isSafePayment(unsafePayment4) shouldBe false
    }
  }

  "isSafePayment for Loan" should {
    "return true for safe payments" in {
      val safePayment1 =
        Loan(Price(5, "EUR"), 6)
      val safePayment2 =
        Loan(Price(50000, "RUB"), 10)
      val safePayment3 =
        Loan(Price(1000000, "USD"), 3000)
      val safePayment4 =
        Loan(Price(11, "UAH"), 100000)

      task2.isSafePayment(safePayment1) shouldBe true
      task2.isSafePayment(safePayment2) shouldBe true
      task2.isSafePayment(safePayment3) shouldBe true
      task2.isSafePayment(safePayment4) shouldBe true
    }

    "return false for unsafe payments" in {
      val unsafePayment1 =
        Loan(Price(10, "EUR"), 0)
      val unsafePayment2 =
        Loan(Price(10123, "RUB"), 1)
      val unsafePayment3 =
        Loan(Price(100000, "GBP"), 2)
      val unsafePayment4 =
        Loan(Price(39234, "CNY"), 4)

      task2.isSafePayment(unsafePayment1) shouldBe false
      task2.isSafePayment(unsafePayment2) shouldBe false
      task2.isSafePayment(unsafePayment3) shouldBe false
      task2.isSafePayment(unsafePayment4) shouldBe false
    }
  }
}

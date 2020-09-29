package homeworks.homework1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class task5Test extends AnyWordSpec with Matchers {
  "optionProduct" should {
    "return product if both options are defined" in {
      for {
        x <- 1 to 1000
        y <- 1 to 1000
      } {
        task5.optionProduct(Some(x), Some(y)) shouldBe Some(x * y)
        task5.optionProduct(Some(y), Some(x)) shouldBe Some(x * y)
      }
    }

    "return none if one of options is not defined" in {
      for {
        x <- 1 to 10000
      } {
        task5.optionProduct(Some(x), None) shouldBe None
        task5.optionProduct(None, Some(x)) shouldBe None
      }
    }

    "return none if neither of options is defined" in {
      task5.optionProduct(None, None) shouldBe None
    }
  }
}

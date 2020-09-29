package homeworks.homework1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class task4Test extends AnyWordSpec with Matchers {
  "optionPairMax" should {
    "return max value if both options are defined" in {
      for {
        max <- 1 to 1000
        min <- 1 to max
      } {
        task4.optionPairMax(Some(max), Some(min)) shouldBe Some(max)
        task4.optionPairMax(Some(min), Some(max)) shouldBe Some(max)
      }
    }

    "return value if only one option is defined" in {
      for {
        value <- 1 to 10000
      } {
        task4.optionPairMax(Some(value), None) shouldBe Some(value)
        task4.optionPairMax(None, Some(value)) shouldBe Some(value)
      }
    }

    "return none if neither of options is defined" in {
      task4.optionPairMax(None, None) shouldBe None
    }
  }
}

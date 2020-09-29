package homeworks.homework1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class task1Test extends AnyWordSpec with Matchers {
  val narayanaCowsSequence =
    List(1, 1, 1, 2, 3, 4, 6, 9, 13, 19, 28, 41, 60, 88, 129, 189, 277, 406, 595, 872, 1278, 1873, 2745, 4023, 5896, 8641, 12664, 18560, 27201, 39865, 58425, 85626, 125491, 183916, 269542, 395033, 578949, 848491, 1243524, 1822473, 2670964, 3914488, 5736961, 8407925)

  "narayanaCows" should {

    "calculate first 44 numbers correctly" in {
      List.tabulate(narayanaCowsSequence.size)(task1.narayanaCows) shouldBe narayanaCowsSequence
    }

    "not fail with stack overflow" in {
      task1.narayanaCows(100000)
    }

  }
}

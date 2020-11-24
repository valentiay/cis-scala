package seminars.seminar11.math

import io.finch._

object MathService {
  def sum(x: Int, y: Int): Output[Int] = Ok(x + y)
  def product(x: Int, y: Int): Output[Int] = Ok(x * y)
  def difference(x: Int, y: Int): Output[Int] = Ok(x - y)
  def division(x: Int, y: Int): Output[Int] = Ok(x / y)
}

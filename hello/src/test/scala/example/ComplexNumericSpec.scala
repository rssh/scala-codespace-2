package example

import org.scalatest._
import example.ImplicitHolder._

class ComplexNumericSpec extends FlatSpec with Matchers {
  "ComplexNumeric" should "Have addition and substraction" in {
    val n1 = ComplexNumber(1,2)
    val n2 = ComplexNumber(1,4)

    assert(n1 + n2 == ComplexNumber(2,6))
    assert(n1 - n2 == ComplexNumber(0,-2))
  }

  "Complex Numbers" should("Have a fluent syntax") in {
    assert(ComplexNumber(1,2) == 1 + I * 2)
    assert(ComplexNumber(3,2) == 1 + I * 1 + 2 + I * 1, "Addition")
    // Not working - operator precedence matters
    // assert(ComplexNumber(3,2) == 5 + I * 3 - 2 + I * 1, "Substraction")
  }
}

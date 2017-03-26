package phonecode

import org.scalatest._

class PhoneCodeSpec extends FlatSpec {

  val phoneCode = new PhoneCode
  import phoneCode._

  "Map charsToDigits" should "for char `t` returns digit `4`" in {
    assert(charsToDigits('t') === 4)
  }

  it should "for Capital char `E` returns `0`" in {
    assert(charsToDigits('E') === 0)
  }

  "method wordToDigits" should "translate lowercase `fort` into `4824`" in {
    assert(wordToDigits("fort") === "4824")
  }

  it should "translate Uppercase `Wasser` into `253302`" in {
    assert(wordToDigits("Wasser") === "253302")
  }

}

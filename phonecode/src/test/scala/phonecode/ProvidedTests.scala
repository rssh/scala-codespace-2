package phonecode

import org.scalatest._

class ProvidedTestsSpec extends FunSuite {

  val phoneCode = new PhoneCode

  test("5624-82: mir Tor (2)") {
    assert(phoneCode.encodePhoneNumber("5624-82") === List("mir Tor", "Mix Tor"))
  }

  test("4824: Torf (3)") {
    assert(phoneCode.encodePhoneNumber("4824") === List("Torf", "fort", "Tor 4"))
  }
}

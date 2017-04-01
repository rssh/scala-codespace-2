package phonecode

import org.scalatest._

class EncodePhoneNumberSpec extends FlatSpec with Matchers with ResourceFilesReader {

  val dictionaryResourcePath = resourceAsString(List("test.w")).getOrElse(throw new IllegalArgumentException)

  val phoneCode = new PhoneCode(dictionaryResourcePath)
  import phoneCode._

  "encodePhoneNumber" should "encode `5624-82` to [mir Tor, Mix Tor]" in {
    encodePhoneNumber("5624-82") should contain only ("mir Tor", "Mix Tor")
  }

  it should "encode `4824` to [Torf, fort, Tor 4]" in {
    encodePhoneNumber("4824") should contain only ("Torf", "fort", "Tor 4")
  }

  it should "encode `10/783--5` to [neu o\"d 5, je bo\"s 5, je Bo\" da]" in {
    encodePhoneNumber("10/783--5") should contain only ("neu o\"d 5", "je bo\"s 5", "je Bo\" da")
  }

  it should "encode `381482` to [so 1 Tor]" in {
    encodePhoneNumber("381482") should contain only "so 1 Tor"
  }

  it should "encode `04824` to [0 Torf]" in {
    encodePhoneNumber("04824") should contain only ("0 Torf", "0 fort", "0 Tor 4")
  }
}

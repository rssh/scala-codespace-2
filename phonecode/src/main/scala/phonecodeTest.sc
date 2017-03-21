import phonecode._

val phoneCode = new PhoneCode
phoneCode.dictionaryMapping

//val word = "Fee"
//phoneCode.charsToDigits('F')
//phoneCode.charsToDigits('e')


val rawPhoneNumber = "04824"

val phoneNumber = rawPhoneNumber

//for (i <- 0 to phoneNumber.length) yield phoneNumber.splitAt(i)

def encodePhoneNumber(fullPhoneNumber: String): List[String] = {
  val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)

  def numberCombinations(
                          phoneNumber: String,
                          phrase: String,
                          digitUnused: Boolean = true
                        ): List[String] = {
    (for {
      i <- 0 to phoneNumber.length
      (first, last) = phoneNumber.splitAt(i)
      word = phoneCode.dictionaryMapping.get(first)
      //      word = phoneCode.dictionaryMapping.getOrElse(first, "")
      //      if word.length > 0
      if word.isDefined ||
        word.isEmpty && first.length == 1 && digitUnused ||
        word.isEmpty && last.length == 1 && digitUnused
    } yield {
      println(word)
      //        numberCombinations(last, phrase + word)
      word match {
        case Some(w) if last.isEmpty => phrase + w
        case Some(w) => numberCombinations(last, phrase + w)
        case None if digitUnused & first.length == 1 => numberCombinations(last, phrase + first + " ", digitUnused = false)
        case None if digitUnused & last.length == 1 => phrase + " " + last
        case None => phrase
      }
    }
      ).map(_.toString)
      .toList
  }

  numberCombinations(purePhoneNumber, "")
}

encodePhoneNumber(rawPhoneNumber)

/*
val phn = "04824"
val a = "04"
phn.substring(a.length)


val purePhoneNumber = rawPhoneNumber.filter(_.isDigit)
def encodePhoneNumber(phoneNumber: String, digitUsed: Boolean = false): String = {
    phoneNumber.foldLeft("") { (acc: String, ch: Char) =>
      val validWord = phoneCode.dictionaryMapping.get(acc)
      validWord match {
        case Some(word) => word + " " + encodePhoneNumber(phoneNumber.substring(acc.length))
        case None if acc.length == 1 && !digitUsed => acc + " " + encodePhoneNumber(phoneNumber.substring(acc.length), true)
        case None if !acc.last.isDigit && !digitUsed => acc + encodePhoneNumber(phoneNumber.substring(acc.length), true)
        case None => ""
      }
    }
  }
}
encodePhoneNumber(purePhoneNumber)
*/

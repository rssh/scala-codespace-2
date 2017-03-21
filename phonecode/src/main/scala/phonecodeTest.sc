import phonecode._

val phoneCode = new PhoneCode
phoneCode.dictionaryMapping

//val word = "Fee"
//phoneCode.charsToDigits('F')
//phoneCode.charsToDigits('e')

import phoneCode._

dictionary.groupBy(wordToDigits)



val rawPhoneNumber = "04824"

val phoneNumber = rawPhoneNumber

//for (i <- 0 to phoneNumber.length) yield phoneNumber.splitAt(i)

def encodePhoneNumber(fullPhoneNumber: String): Seq[String] = {
  val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)


  def generateWord(part:String, prevDigit:Boolean): Seq[String]=
  {
    phoneCode.dictionaryMapping.get(part).getOrElse(Seq())
  }

  val lengths = phoneCode.dictionaryMapping.keys.map(_.length)
  val minLen: Int = lengths.min
  val maxLen: Int = lengths.max
  //val maxLen: = dictionaryMapping.keys.maxBy(_.length)


  def numberCombinations( rest: String,
                          phrase: String,
                          prevDigit: Boolean): Seq[String] =
  {

    val l1: Seq[String] = if (prevDigit) Seq() else {
        numberCombinations(rest.tail, phrase + rest.head, true)
      }

    val l2 = for{
      i <- minLen to maxLen
      (first,next) = rest.splitAt(i)
      w <- generateWord(first,prevDigit)
      x <- numberCombinations(next,phrase+"_"+w,false)
    } yield x

    l1 ++ l2

  }

  /*
  def numberCombinations(
                          phoneNumber: String,
                          phrase: String,
                          digitUnused: Boolean = true
                        ): List[String] = {
    for {
      i <- 0 to phoneNumber.length
      (first, last) = phoneNumber.splitAt(i)
      word = phoneCode.dictionaryMapping.get(first)
      //      word = phoneCode.dictionaryMapping.getOrElse(first, "")
      //      if word.length > 0
      if word.isDefined ||
        word.isEmpty && first.length == 1 && digitUnused ||
        word.isEmpty && last.length == 1 && digitUnused

      _ = println(word)
      //        numberCombinations(last, phrase + word)

      r <- word match {
        case Some(w) if last.isEmpty => phrase + w
        case Some(w) => numberCombinations(last, phrase + w)
        case None if digitUnused & first.length == 1 => numberCombinations(last, phrase + first + " ", digitUnused = false)
        case None if digitUnused & last.length == 1 => phrase + " " + last
        case None => phrase
      }

    } yield r


    ???

  }
  */

  numberCombinations(purePhoneNumber, "", false)
}

encodePhoneNumber(rawPhoneNumber)

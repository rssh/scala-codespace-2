import phonecode._

val phoneCode = new PhoneCode

//val word = "Fee"
//phoneCode.charsToDigits('F')
//phoneCode.charsToDigits('e')

import phoneCode._

dictionary.groupBy(wordToDigits)

val rawPhoneNumber = "10/783--5" //"04824" //"381482" //

val phoneNumber = rawPhoneNumber

//for (i <- 0 to phoneNumber.length) yield phoneNumber.splitAt(i)

def encodePhoneNumber(fullPhoneNumber: String): Set[String] = {
  val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)
  println(s"Pure PhoneNumber: $purePhoneNumber")

  def generateWord(part: String, prevDigit: Boolean): Seq[String] = {
    val phrase = phoneCode.dictionaryMapping.getOrElse(part, Seq()) //get(part).getOrElse(Seq())
    println(s"Generate word for code [$part]: [$phrase]")
    phrase
  }

  val lengths = phoneCode.dictionaryMapping.keys.map(_.length)
  val minLen: Int = lengths.min
  val maxLen: Int = lengths.max
  println(s"Length min: $minLen, max: $maxLen")
  //val maxLen: = dictionaryMapping.keys.maxBy(_.length)

  def numberCombinations(
                          rest: String,
                          phrase: String,
                          prevDigit: Boolean
                        ): Seq[String] = {
    if (!rest.isEmpty) {

      val planePhrase = for {
        i <- minLen to maxLen
        (first, next) = rest.splitAt(i);
        w <- generateWord(first, prevDigit)
        x <- numberCombinations(next, phrase + " " + w, false)
      } yield x
      println(s"planePhrase: $planePhrase")

      val dgtPhrase: Seq[String] = {
        if (prevDigit) Seq()
        else if (!planePhrase.isEmpty & phrase.isEmpty) Seq()
        else {
          numberCombinations(rest.tail, phrase + " " + rest.head, true)
        }
      }
      println(s"dgtPhrase: $dgtPhrase")

      planePhrase ++ dgtPhrase
    } else Seq(phrase)
  }

  numberCombinations(purePhoneNumber, "", false)
}.map(_.trim).toSet

encodePhoneNumber(rawPhoneNumber)


phoneCode.dictionaryMapping

//7830 -> List(bo"s), 780 -> List(Bo")

phoneCode.charsToDigits.get('"')


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

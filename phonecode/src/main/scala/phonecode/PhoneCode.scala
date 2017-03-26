package phonecode

/*
* 1. Cleanup phone code (remove slash, etc).
* 2. Load dictionary from a file (alphabetically sorted).
* 3. Find each possible encoding
*
* Notes:
* - words contain dashes (-) and double quotes (") which ignored in encoding, but should be printed
* - Leading non-letters do not occur in the dictionary
* - Encodings of phone numbers can consist of a single word or of multiple words separated by spaces.
* - if at a particular point no word at all from the dictionary can be inserted, a single digit from the phone number can be copied to the encoding instead.
* - Two subsequent digits are never allowed
* */

//object PhoneCodeObj {
//  def main(args: Array[String]): Unit = {
//    require(len(args) == 2, "Specify dictionary and phone code files only.")
//  }
//}
trait CharsEncoding {
  private val chars: Array[String] = Array("e", "jnq", "rwx", "dsy", "ft", "am", "civ", "bku", "lop", "ghz")
  val charsToDigits: Map[Char, Int] = (for {
    (group, digit) <- chars.zipWithIndex ++ chars.map(_.toUpperCase).zipWithIndex
    letter <- group
  } yield letter -> digit).toMap
}

class PhoneCode extends CharsEncoding {
  // TODO: program should start with the dictionary and phone codes list as arguments:
  // > phonecode test.w test.t
  private val dictionaryFile = List("test.w")
  private val phoneNumberListFile = List("test.t")
  val dictionary: List[String] = loadDictionary(dictionaryFile)
  val phoneNumberList: List[String] = loadDictionary(phoneNumberListFile)

  def wordToDigits(word: String): String = {
    //    word.filter(_.isLetter)
    //      .map{ char => charsToDigits(char).toString}.mkString("").toInt
    val len = word.length - 1
    word.filter(_.isLetter)
      .zipWithIndex
      .map { case (char, ind) => charsToDigits(char) * math.pow(10, len - ind).toInt }
      .sum
  }.toString

  def encodePhoneNumber(phoneNumber: String): List[String] = ???
//  {
//    val purePhoneNumber = phoneNumber.filter(_.isDigit)
//    def encodeNum(num: String): List[String] = {
//      ???
//    }
//  }

  //val dictionaryMapping: Map[String, String] = dictionary.map(w => wordToDigits(w).toString -> w).toMap

  val dictionaryMapping: Map[String, Seq[String]] = {

    dictionary.groupBy(wordToDigits)

    /*
    val grouped = dictionary.map(
      w => wordToDigits(w) -> w
    ).groupBy { case (d, w) => d }
    grouped.mapValues(_.map{case (d,w) => w })
    */
  }

}


/*
* def encodePhoneNumber(fullPhoneNumber: String): List[String] = {
  val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)

  def numberCombinations(
                          phoneNumber: String,
                          phrase: List[String],
                          digitUnused: Boolean = true,
                          break: Boolean = false
                        ): List[String] = {
    if (break) List("")
    else {
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
//        println(word)
                numberCombinations(last, phrase ++ word)
//        word match {
//          case Some(w) => numberCombinations(last, phrase + w)
//          case None if digitUnused & first.length == 1 => numberCombinations(last, phrase + first, digitUnused = false)
//          case None if digitUnused & last.length == 1 => phrase + last
//          case None => phrase
//        }
      }).flatten.toList
    }
  }

  numberCombinations(purePhoneNumber, List[String]())
}

* */
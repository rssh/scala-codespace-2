package phonecode

/*
* 1. Cleanup phone code (remove slashes, dashes, etc).
* 2. Load dictionary from a file (alphabetically sorted).
* 3. Find each possible encoding
*
* Notes:
* - words contain dashes (-) and double quotes (") which ignored in encoding, but should be printed
* - Leading non-letters do not occur in the dictionary
* - Encodings of phone numbers can consist of a single word or of multiple words separated by spaces.
* - if at a particular point no word at all from the dictionary can be inserted, a single digit from the phone number can be copied to the encoding instead.
* - two subsequent digits are never allowed
* - digit can be used only if there is no word in the dictionary that can be used in the partial encoding starting at this character
* */

//object PhoneCodeObj {
//  def main(args: Array[String]): Unit = {
//    require(len(args) == 2, "Specify dictionary and phone code files only.")
// TODO: iterate over provided list of phone numbers and print in format `phoneNumber: encoding` for each encoding
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

  def wordToDigits(word: String): String = //{
        word.filter(_.isLetter)
          .map{ char => charsToDigits(char).toString}.mkString("")
//    val len = word.filter(_.isLetter).length - 1
//    word.filter(_.isLetter)
//      .zipWithIndex
//      .map { case (char, ind) => charsToDigits(char) * math.pow(10, len - ind).toInt }
//      .sum
//  }.toString

  val dictionaryMapping: Map[String, Seq[String]] = dictionary.groupBy(wordToDigits)

  def encodePhoneNumber(fullPhoneNumber: String): Set[String] = {
//    println
//    println(s"Encode phone number: $fullPhoneNumber")
    val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)

    def generateWord(part: String, prevDigit: Boolean): Seq[String] = {
      dictionaryMapping.getOrElse(part, Seq()) //get(part).getOrElse(Seq())
    }

    val lengths = dictionaryMapping.keys.map(_.length)
    val minLen: Int = lengths.min
    val maxLen: Int = lengths.max
    //val maxLen: = dictionaryMapping.keys.maxBy(_.length)

    def numberCombinations(
      rest: String,
      phrase: String,
      prevDigit: Boolean
    ): Seq[String] = {
//      println(s"Rest phrase: $rest")
      if (!rest.isEmpty) {

        val planePhrase = for {
          i <- minLen to maxLen
          (first, next) = rest.splitAt(i)
          w <- generateWord(first, prevDigit)
          x <- numberCombinations(next, phrase + " " + w, false)
        } yield x
//        println(s"  planePhrase: $planePhrase")

        val dgtPhrase: Seq[String] = {
          if (prevDigit || !planePhrase.isEmpty) Seq()
          else {
            numberCombinations(rest.tail, phrase + " " + rest.head, true)
          }
        }
//        println(s"  dgtPhrase: $dgtPhrase")

        planePhrase ++ dgtPhrase
      } else Seq(phrase)
    }

    numberCombinations(purePhoneNumber, "", false)
  }.map(_.trim).toSet

}
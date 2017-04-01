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

object PhoneCodeMain extends FilesReader {
  def main(args: Array[String]): Unit = {
    require(args.length == 2, "Specify dictionary and phone code files only.")
    println("It's alive!")
    val dictionaryFilePath = args(0)
    val phoneNumberListPath = args(1)

    //    println(s"dictionaryFilePath: $dictionaryFilePath")
    //    println(s"phoneNumberListPath: $phoneNumberListPath")
    //    println(classesDir)
    val phoneCode = new PhoneCode(dictionaryFilePath)

    // TODO: iterate over provided list of phone numbers and print in format `phoneNumber: encoding` for each encoding
    /*    val phoneNumberList = loadFileAsStream(phoneNumberListPath)
    val result = for {
      phoneNumber <- phoneNumberList
      phoneMatch <- phoneCode.encodePhoneNumber(phoneNumber)
    } yield phoneMatch
    result foreach (println)*/
  }
}

trait CharsEncoding {
  private val chars: Array[String] = Array("e", "jnq", "rwx", "dsy", "ft", "am", "civ", "bku", "lop", "ghz")
  val charsToDigits: Map[Char, Int] = (for {
    (group, digit) <- chars.zipWithIndex ++ chars.map(_.toUpperCase).zipWithIndex
    letter <- group
  } yield letter -> digit).toMap
}

trait FilesReader {

  def using[T <: { def close() }, R](resource: T)(func: T => R): R =
    {
      try {
        func(resource)
      } finally {
        if (resource != null) resource.close()
      }
    }

  def loadDictionaryFile(dictionaryPath: String): List[String] = {
    using(scala.io.Source.fromFile(dictionaryPath)) { source =>
      source.getLines.toList
    }
  }

  def loadFileAsStream(path: String): Iterator[String] = {
    using(scala.io.Source.fromFile(path)) { source =>
      source.getLines
    }
  }

  //  def loadFileAsStream(dictionaryPath: String) = {
  //  import java.io.FileNotFoundException
  //    lazy val source = scala.io.Source.fromFile(dictionaryPath)
  //    try {
  //      source.getLines
  //    } catch {
  //      case e: FileNotFoundException => sys.error(s"Could not load word list, dictionary file not found")
  //      case e: Exception =>
  //        sys.error("Could not load word list: " + e)
  //        throw e
  //    } finally {
  //      source.close()
  //    }
  //  }

}

class PhoneCode(dictionaryFile: String) extends CharsEncoding with FilesReader {
  // > phonecode test.w test.t
  //  println(getClass.getResource(".").toURI)
  //  val dictionary: List[String] = loadDictionary(dictionaryFile)
  val dictionary = loadDictionaryFile(dictionaryFile)
  //  val phoneNumberList: List[String] = loadDictionary(phoneNumberListFile)
  val dictionaryMapping: Map[String, Seq[String]] = dictionary.groupBy(wordToDigits)

  //    val len = word.filter(_.isLetter).length - 1
  //    word.filter(_.isLetter)
  //      .zipWithIndex
  //      .map { case (char, ind) => charsToDigits(char) * math.pow(10, len - ind).toInt }
  //      .sum
  //  }.toString

  def wordToDigits(word: String): String = //{
    word.filter(_.isLetter)
      .map { char => charsToDigits(char).toString }.mkString("")

  def generateWord(part: String, prevDigit: Boolean): Seq[String] = {
    dictionaryMapping.getOrElse(part, Seq()) //get(part).getOrElse(Seq())
  }

  def encodePhoneNumber(fullPhoneNumber: String): Set[String] = {
    //    println
    //    println(s"Encode phone number: $fullPhoneNumber")
    val purePhoneNumber = fullPhoneNumber.filter(_.isDigit)

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
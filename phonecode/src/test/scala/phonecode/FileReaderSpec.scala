package phonecode

import java.io.File
import org.scalatest.FlatSpec

class FileReaderSpec extends FlatSpec with FilesReader {
  "FilesReader loadDictionaryFile" should "return files's content" in {
    val classesDir = new File(getClass.getResource(".").toURI)
    val projectDir = classesDir.getParentFile.getParentFile.getParentFile.getParentFile
    val resourceFileName: String = projectDir.toString + "/src/test/resources/test.t"
//    println(s"resourceFileName: $resourceFileName")
    val fileContent = loadDictionaryFile(resourceFileName)
//    println(s"fileContent: ${fileContent(1)}")
    assert(fileContent(0) === "112")
  }
}

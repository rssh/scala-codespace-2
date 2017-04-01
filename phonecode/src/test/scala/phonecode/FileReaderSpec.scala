package phonecode

import java.io.File
import org.scalatest.FlatSpec

class FileReaderSpec extends FlatSpec with FilesReader {
  "FilesReader loadDictionaryFile" should "return file's content" in {
    val classesDir = new File(getClass.getResource(".").toURI)
    val projectDir = classesDir.getParentFile.getParentFile.getParentFile.getParentFile
    val resourceFileName: String = projectDir.toString + "/src/test/resources/test.t"
    val fileContent = loadDictionaryFile(resourceFileName)

    assert(fileContent.head === "112")
  }
}

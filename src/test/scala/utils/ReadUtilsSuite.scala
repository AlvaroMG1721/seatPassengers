package utils

import es.amarqueg.utils.ReadUtils.parsePassengersFile
import java.io.FileNotFoundException
import org.scalatest.{FunSpec, Matchers}

class ReadUtilsSuite extends FunSpec with Matchers {

  describe("The parsing of the input passengers file") {

    val passengersFile = getClass.getResource("/passengersFile.txt").getPath

    it("Should throw FileNotFoundException if the file does not exist") {
      val unexistingFile = "notAFile"

      val parsedFile = parsePassengersFile(unexistingFile)
      assertThrows[FileNotFoundException] {
        parsedFile.get
      }
    }

    it("Should create a seatsMatrix with the proper dimensions using the first line of the file"){
      val parsedFile = parsePassengersFile(passengersFile)
      val seatsMatrix = parsedFile.get._1

      val sizeRows = seatsMatrix(0).length
      val numberRows = seatsMatrix.length

      assert(sizeRows === 4)
      assert(numberRows === 4)
      assert(sizeRows !== 3)
    }

    it("Should create a groups with the contents of the file, excluding the first line, sorted by length"){
      val parsedFile = parsePassengersFile(passengersFile)
      val groups = parsedFile.get._2

      val expectedList = List("4", "5", "9", "7", "1", "2", "3", "8")

      assert(groups.flatMap(_.passengers).toList.map(_.toString) == expectedList)
    }
  }
}

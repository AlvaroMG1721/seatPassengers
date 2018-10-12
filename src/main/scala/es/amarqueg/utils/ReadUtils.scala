package es.amarqueg.utils

import es.amarqueg.model.{Group, Passenger}
import scala.util.Try

object ReadUtils {

  // Implementation of the 'Loan Pattern', which ensures
  // that a resource is deterministically disposed of once it goes out of scope
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }

  // Reads a text file into a list of Strings using Try
  def readTextFileWithTry(fileName: String): Try[List[String]] = { //TODO: Tests
    Try {
      val lines = using(io.Source.fromFile(fileName)) { source =>
        (for (line <- source.getLines) yield line).toList
      }
      lines
    }
  }

  def parsePassengersFile(fileName: String): Try[(Array[Array[Int]], Seq[Group])] = {
    Try {
      val fileLines = readTextFileWithTry(fileName).get

      // Parsing seatsMatrix
      val dimensionArray = fileLines.head.split("\\s+").map(_.toInt)
      val seatsMatrix = Array.ofDim[Int](dimensionArray(1), dimensionArray(0))

      // Parsing groups
      val groups = fileLines.map(line => line.split("\\s+")).tail.sortWith(_.length > _.length)
          .map { line => line.map(string => Passenger(string)).toSeq }.map(line => Group(line))

      (seatsMatrix, groups)
    }
  }
}

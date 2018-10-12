package es.amarqueg.app

import es.amarqueg.algorithm.CombineGroups.selectBetterGroupsForPlaneDimensions
import es.amarqueg.algorithm.OptimizeSeats.optimizeSeats
import es.amarqueg.algorithm.SplitOverSizedGroups.splitOverSizedGroups
import es.amarqueg.utils.ReadUtils.parsePassengersFile
import es.amarqueg.utils.WriteUtils.printResults
import java.io.FileNotFoundException

object SeatPassengersApp {

  final val ERROR_CODE = 1
  val usage = """
    Usage: scala seatPassengers.jar filename
  """

  def main(args: Array[String]): Unit = {

    if (args.length != 1) {
      println("Exiting with error: The program expects one parameter.")
      println(usage)
      System.exit(ERROR_CODE)
    }

    val parsedFile = parsePassengersFile(args(0))
    parsedFile recover {
      case ex: FileNotFoundException => {
        println(s"The given file could not be found: ${ex.getMessage}")
        System.exit(ERROR_CODE)
      }
      case ex: Exception => {
        println(s"Unexpected exception while parsing input file: ${ex.getMessage}")
        System.exit(ERROR_CODE)
      }
    }

    val (seatsMatrix, groups) = parsedFile.get

    val groupsToEnter = selectBetterGroupsForPlaneDimensions(groups, seatsMatrix)
    val groupsSplittedIfOverSized = splitOverSizedGroups(groupsToEnter, rowLength = seatsMatrix(0).length)

    val (placementMatrix, unsatisfied) = optimizeSeats(groupsSplittedIfOverSized, seatsMatrix)

    printResults(placementMatrix, unsatisfied)
  }
}

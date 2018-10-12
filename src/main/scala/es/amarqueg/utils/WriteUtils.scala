package es.amarqueg.utils

import es.amarqueg.algorithm.OptimizeSeats

object WriteUtils {

  def printResults(placementMatrix: Array[Array[Int]], unsatisfied: Int): Unit = {

    val passengersInside = placementMatrix.flatten.count(_ != OptimizeSeats.EMPTY_SEAT)
    val satisfied = passengersInside - unsatisfied
    val percentageSatisfied = (satisfied.toFloat/passengersInside * 100).round

    placementMatrix.foreach(array => println(array.mkString(" ")))
    println(percentageSatisfied + "%")
  }

}

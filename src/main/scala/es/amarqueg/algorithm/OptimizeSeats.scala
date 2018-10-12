package es.amarqueg.algorithm

import es.amarqueg.model.{Group, Passenger}
import scala.annotation.tailrec

object OptimizeSeats {

  final val EMPTY_SEAT = 0
  var numUnsatisfied = 0

  @tailrec
  def optimizeSeats(groups: Seq[Group], seatsMatrix: Array[Array[Int]]): (Array[Array[Int]], Int) = {

    if (groups.isEmpty) {
      (seatsMatrix, numUnsatisfied)
    } else {
      val currentGroup = groups.head
      val isPlaced = placeGroupTogether(currentGroup, seatsMatrix)
      if (!isPlaced) {
        println("Could not fit passengers together: " + currentGroup.passengers.mkString(", "))
        placeNotSatisfied(currentGroup, seatsMatrix)
      }
      optimizeSeats(groups.tail, seatsMatrix)
    }
  }

  // Finds the first row that fits a group size and window preference and fills it
  def placeGroupTogether(group: Group, seatsMatrix: Array[Array[Int]]): Boolean = {

    val rowsOrderedByNumWindows = seatsMatrix.sortWith(countRemainingWindows(_) > countRemainingWindows(_))

    var isPlaced = false
    for (rowNumber <- rowsOrderedByNumWindows.indices if !isPlaced) {
      if (countRemainingSeats(rowsOrderedByNumWindows(rowNumber)) >= group.size) {
        fillRow(rowsOrderedByNumWindows(rowNumber), group)
        isPlaced = true
      }
    }
    isPlaced
  }

  // Fills a given row with all passengers of a given list, based on window preference
  def fillRow(seatsArray: Array[Int], group: Group) = {

    val windowsLeft = countRemainingWindows(seatsArray)
    val numWindowPreferencePassengers = group.numWindowPreferencePassengers
    val passengersBasedOnWindow = orderPassengersBasedOnWindow(group, windowsLeft)

    if(windowsLeft == 2) {
      passengersBasedOnWindow.foreach(passenger => seatsArray(seatsArray.indexOf(EMPTY_SEAT)) = passenger.id)
      val numWindowsTaken = if (group.size == seatsArray.length) 2 else 1
      numUnsatisfied += Math.max(0, numWindowPreferencePassengers - numWindowsTaken)

    } else if (windowsLeft == 1 && group.hasPassengerWithWindowPreference) {
      passengersBasedOnWindow.foreach(passenger => seatsArray(seatsArray.lastIndexOf(EMPTY_SEAT)) = passenger.id)
      numUnsatisfied += group.numWindowPreferencePassengers - 1

    } else {
      passengersBasedOnWindow.foreach(passenger => seatsArray(seatsArray.indexOf(EMPTY_SEAT)) = passenger.id)
      numUnsatisfied += group.numWindowPreferencePassengers
    }
  }

  // Returns the number of empty seats left in a row
  def countRemainingSeats(seatsArray: Array[Int]): Int = {
    seatsArray.count(_ == EMPTY_SEAT)
  }

  def orderPassengersBasedOnWindow(group: Group, windowsLeft: Int): Seq[Passenger] = {

    if (!group.hasPassengerWithWindowPreference) {
      group.passengers
    } else {

      if (windowsLeft == 1) {
        shiftPassengerRight(group.passengers)
      } else if (windowsLeft == 2 && group.numWindowPreferencePassengers > 1) {
        shiftPassengerBothSides(group.passengers)
      } else {
        shiftPassengerRight(group.passengers).reverse
      }
    }
  }

  // Shifts a window passenger to the right side of the array
  def shiftPassengerRight(passengers: Seq[Passenger]): Array[Passenger] = {
    val array = passengers.toArray
    if (array(array.length - 1).hasWindowPreference) {
      array
    } else {
      val index = array.indexWhere(_.hasWindowPreference == true)
      val value = array(index)
      System.arraycopy(array, index + 1, array, index, array.length - index - 1)
      array(array.length - 1) = value
      array
    }
  }

  def shiftPassengerBothSides(passengers: Seq[Passenger]): Array[Passenger] = {
    val shiftedListRight = shiftPassengerRight(passengers)
    shiftPassengerRight(shiftedListRight.dropRight(1)).reverse :+ shiftedListRight.last
  }

  def countRemainingWindows(seatsArray: Array[Int]): Int = {
    if (seatsArray(0) == 0) 2
    else if (seatsArray(seatsArray.length - 1) == 0) 1
    else 0
  }

  def placeNotSatisfied(group: Group, rows: Array[Array[Int]]): Unit = {

    numUnsatisfied += group.size

    group.passengers.foreach { passenger =>
      var isPlaced = false
      for (rowNumber <- rows.indices if !isPlaced) {
        if (countRemainingSeats(rows(rowNumber)) >= 1) {
          val currentRow = rows(rowNumber)
          currentRow(currentRow.indexOf(EMPTY_SEAT)) = passenger.id
          isPlaced = true
        }
      }
    }
  }

}

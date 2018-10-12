package algorithm

import es.amarqueg.algorithm.OptimizeSeats.{countRemainingSeats, orderPassengersBasedOnWindow}
import es.amarqueg.model.{Group, Passenger}
import org.scalatest.{FunSpec, Matchers}

class OptimizeSeatsSuite extends FunSpec with Matchers {

  describe("The countRemainingSeats method") {
    it("Should return the number of zeroes in an Array[Int]") {
      val array = Array(1, 5, 6, 0)
      val zeroes = countRemainingSeats(array)
      assert(zeroes === 1)
    }
  }

  describe("The orderBasedOnWindow method") {

    it("Should place a passenger with window preference to the left when there is two windows left " +
        "and one passenger with preference") {

      val passengersArrayTwoWindows = Array(Passenger(1,false), Passenger(2,false), Passenger(3,true))
      val expectedArrayTwoWindows = Array(Passenger(3,true), Passenger(2,false), Passenger(1,false)).mkString(",")

      assert(orderPassengersBasedOnWindow(Group(passengersArrayTwoWindows), 2).mkString(",") === expectedArrayTwoWindows)
    }

    it("Should place a passenger with window preference on each side when there is two windows left " +
        "and two passengers with preference") {

      val passengersArrayTwoWindows = Array(Passenger(1,false), Passenger(2,true), Passenger(3,true), Passenger(4,false))
      val expectedArrayTwoWindows = Array(Passenger(3,true), Passenger(4,false), Passenger(1,false), Passenger(2,true)).mkString(",")

      assert(orderPassengersBasedOnWindow(Group(passengersArrayTwoWindows), 2).mkString(",") === expectedArrayTwoWindows)
    }

    it("Should return the same array if there are no passengers with window preference") {

      val passengersArray = Array(Passenger(1,false), Passenger(2,false), Passenger(3,false))

      assert(orderPassengersBasedOnWindow(Group(passengersArray), 1).mkString(",") === passengersArray.mkString(","))
    }
  }
}

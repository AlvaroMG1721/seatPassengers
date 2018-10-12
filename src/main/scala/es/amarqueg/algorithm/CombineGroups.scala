package es.amarqueg.algorithm

import es.amarqueg.model.Group
import scala.collection.mutable.ListBuffer

object CombineGroups {

  def selectBetterGroupsForPlaneDimensions(groups: Seq[Group], seatsMatrix: Array[Array[Int]]) : Seq[Group] = {

    val numTotalSeats = seatsMatrix.length * seatsMatrix(0).length
    val numTotalPassengers = groups.map(_.size).sum
    val sizesCombination = getBetterGroupsSizesCombination(groups.map(_.size), numTotalSeats)
    val passengersAllocated = sizesCombination.sum

    println("Number of passengers left outside the plane: " + (numTotalPassengers - passengersAllocated))

    getGroupsMatchingSizes(sizesCombination, groups)
  }

  // Finds sizes from the groups in the list that fit in the plane
  def getBetterGroupsSizesCombination(groupSizes: Seq[Int], limit: Int): Seq[Int] = {
    val subset = groupSizes.filter(limit.>=)
    if (subset.sum <= limit) subset
    else {
      val res = (1 to subset.length).view
          .flatMap(subset.combinations)
          .find(_.sum == limit)
      if (res.isEmpty) getBetterGroupsSizesCombination(subset, limit-1)
      else res.get
    }
  }

  // Finds groups matching the previous sizes
  def getGroupsMatchingSizes(sizes: Seq[Int], groups: Seq[Group]): Seq[Group] = {
    val groupsVar = groups.to[ListBuffer]
    sizes.map{ size =>
      val groupSelected = groupsVar.filter(_.size == size).head
      groupsVar -= groupSelected
      groupSelected
    }
  }
}

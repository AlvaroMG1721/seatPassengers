package es.amarqueg.algorithm

import es.amarqueg.model.Group
import scala.collection.mutable.ListBuffer

object SplitOverSizedGroups {

  def splitOverSizedGroups(groups: Seq[Group], rowLength: Int): Seq[Group] = {
    var groupsVar = new ListBuffer[Group]()
    groups.foreach { group =>
      groupsVar ++= group.passengers.grouped(rowLength).toList.map(Group)
    }
    groupsVar.toList
  }

}

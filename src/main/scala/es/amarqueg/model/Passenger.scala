package es.amarqueg.model

class Passenger(val id: Int, val hasWindowPreference: Boolean) {
  override def toString: String = id.toString
}

object Passenger {
  def apply(string: String): Passenger = {
    if (string.endsWith("W")) {
      new Passenger(string.dropRight(1).toInt, true)
    } else {
      new Passenger(string.toInt, false)
    }
  }
  def apply(id: Int, isWindowPreference: Boolean): Passenger = {
    new Passenger(id, isWindowPreference)
  }
}
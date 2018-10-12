package es.amarqueg.model

case class Group(passengers: Seq[Passenger]) {
  val numWindowPreferencePassengers: Int = passengers.count(_.hasWindowPreference)
  val hasPassengerWithWindowPreference: Boolean = numWindowPreferencePassengers > 0
  val size: Int = passengers.length
}

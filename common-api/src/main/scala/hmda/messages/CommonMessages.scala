package hmda.messages

object CommonMessages {
  trait Command
  trait Event
  sealed trait Message
  final case object StopActor extends Message
}

package hmda.health

import akka.actor.Props
import hmda.model.actor.HmdaActor

object HmdaHealth {
  val name = "HmdaHealth"
  def props: Props = Props(new HmdaHealth)
}

class HmdaHealth extends HmdaActor {
  override def receive = super.receive orElse {
    case _ =>
  }
}

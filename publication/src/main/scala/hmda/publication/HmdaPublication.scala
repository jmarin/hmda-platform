package hmda.publication

import akka.actor.Props
import hmda.model.actor.HmdaActor

object HmdaPublication {
  val name = "HmdaPublication"
  def props: Props = Props(new HmdaPublication)
}

class HmdaPublication extends HmdaActor {
  override def receive = super.receive orElse {
    case _ =>
  }
}

package hmda.health

import akka.actor.{ActorRef, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import hmda.model.actor.HmdaActor
import hmda.model.cluster.ClusterMembersDetails
import hmda.model.pubsub.HmdaPubSubTopics

object HealthStatusListener {
  def props(actorRef: ActorRef): Props =
    Props(new HealthStatusListener(actorRef))
}

class HealthStatusListener(actorRef: ActorRef) extends HmdaActor {

  val mediator = DistributedPubSub(context.system).mediator

  mediator ! Subscribe(HmdaPubSubTopics.clusterStatusTopic, self)

  override def receive: Receive = super.receive orElse {
    case members: ClusterMembersDetails =>
      actorRef ! members
  }

}

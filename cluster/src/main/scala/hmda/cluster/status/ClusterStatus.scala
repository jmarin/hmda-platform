package hmda.cluster.status

import akka.actor.{Address, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.cluster.{Cluster, MemberStatus}
import com.typesafe.config.ConfigFactory
import hmda.model.actor.HmdaActor
import hmda.model.cluster.{ClusterMembersDetails, MemberDetails}
import hmda.model.messages.CommonMessages.StopActor
import hmda.model.pubsub.HmdaPubSubTopics

import scala.concurrent.duration._

object ClusterStatus {
  final val name = "ClusterStatus"
  sealed trait ClusterStatusCommand
  case object GetMembers extends ClusterStatusCommand
  case object PublishMemberDetails extends ClusterStatusCommand

  def props: Props = Props(new ClusterStatus)
}

class ClusterStatus extends HmdaActor {
  import ClusterStatus._
  import akka.cluster.ClusterEvent._

  val mediator = DistributedPubSub(context.system).mediator

  private var members = Set.empty[MemberDetails]

  implicit val ec = context.dispatcher

  val config = ConfigFactory.load()
  val schedulerStartupDelay =
    config.getInt("hmda.clusterStatusSchedulerStartupDelay")
  val schedulerInterval = config.getInt("hmda.clusterStatusSchedulerInterval")

  context.system.scheduler.schedule(schedulerStartupDelay.milliseconds,
                                    schedulerInterval.seconds,
                                    self,
                                    PublishMemberDetails)

  Cluster(context.system)
    .subscribe(self, InitialStateAsEvents, classOf[ClusterDomainEvent])

  override def receive: Receive = super.receive orElse {
    case GetMembers =>
      sender() ! ClusterMembersDetails(members)

    case PublishMemberDetails =>
      println("Publishing member details")
      mediator ! Publish(HmdaPubSubTopics.clusterStatusTopic,
                         ClusterMembersDetails(members))

    case MemberJoined(member) =>
      log.info("Member joining: {}", member.address)
      members += MemberDetails(member.address, member.status)

    case MemberUp(member) =>
      log.info("Member up: {}", member.address)
      members += MemberDetails(member.address, member.status)

    case MemberLeft(member) =>
      log.info("Member leaving: {}", member.address)
      updateMembers(MemberDetails(member.address, member.status))

    case MemberExited(member) =>
      log.info("Member exiting: {}", member.address)

    case MemberRemoved(member, _) =>
      log.info("Member removed: {}", member.address)
      members -= MemberDetails(member.address, member.status)

    case UnreachableMember(member) =>
      log.info("Member unreachable: {}", member.address)
      updateMembers(MemberDetails(member.address, member.status))

    case ReachableMember(member) =>
      log.info("Member reachable: {}", member.address)
      updateMembers(MemberDetails(member.address, member.status))

    case StopActor =>
      context stop self
  }

  private def updateMembers(memberDetails: MemberDetails) = {}

}

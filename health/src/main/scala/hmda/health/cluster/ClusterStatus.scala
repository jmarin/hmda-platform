package hmda.health.cluster

import akka.actor.{Address, Props}
import akka.cluster.Cluster
import akka.cluster.MemberStatus
import hmda.model.actor.HmdaActor

object ClusterStatus {
  final val name = "ClusterStatus"
  case object GetMembers
  case class MemberDetails(address: Address, status: MemberStatus)
  def props:Props = Props(new ClusterStatus)
}

class ClusterStatus extends HmdaActor {
  import akka.cluster.ClusterEvent._
  import ClusterStatus._

  private var members = Set.empty[MemberDetails]

  Cluster(context.system).subscribe(self, InitialStateAsEvents, classOf[ClusterDomainEvent])

  override def receive: Receive = super.receive orElse {
    case GetMembers =>
      sender() ! members

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
  }

  private def updateMembers(memberDetails: MemberDetails) = ???

}

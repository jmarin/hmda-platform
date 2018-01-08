package hmda.model.cluster

import akka.actor.Address
import akka.cluster.MemberStatus

case class MemberDetails(address: Address, status: MemberStatus)
case class ClusterMembersDetails(membersDetails: Set[MemberDetails])

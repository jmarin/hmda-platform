package hmda.cluster

import akka.actor.ActorSystem
import akka.cluster.Cluster
import com.typesafe.config.ConfigFactory
import hmda.persistence.HmdaPersistence
import hmda.query.HmdaQuery
import org.slf4j.LoggerFactory

object HmdaPlatform extends App {

  val log = LoggerFactory.getLogger("hmda")

  log.info(
    """
      |
      | #     # #     # ######     #       ######                                                     #     #  #####
      | #     # ##   ## #     #   # #      #     # #        ##   ##### ######  ####  #####  #    #    #     # #     #
      | #     # # # # # #     #  #   #     #     # #       #  #    #   #      #    # #    # ##  ##    #     #       #
      | ####### #  #  # #     # #     #    ######  #      #    #   #   #####  #    # #    # # ## #    #     #  #####
      | #     # #     # #     # #######    #       #      ######   #   #      #    # #####  #    #     #   #  #
      | #     # #     # #     # #     #    #       #      #    #   #   #      #    # #   #  #    #      # #   #
      | #     # #     # ######  #     #    #       ###### #    #   #   #       ####  #    # #    #       #    #######|
      |
      |
      """.stripMargin
  )

  val configuration = ConfigFactory.load()
  val clusterRoleConfig = sys.env
    .get("HMDA_CLUSTER_ROLES")
    .map(roles => s"akka.cluster.roles = [$roles]")
    .getOrElse("")
  val clusterConfig =
    ConfigFactory.parseString(clusterRoleConfig).withFallback(configuration)
  val system =
    ActorSystem(clusterConfig.getString("clustering.name"), clusterConfig)
  val cluster = Cluster(system)

  //Start Persistence
  if (cluster.selfRoles.contains(HmdaClusterRoles.persistence)) {
    system.actorOf(HmdaPersistence.props, HmdaPersistence.name)
  }

  //Start Query
  if (cluster.selfRoles.contains(HmdaClusterRoles.query)) {
    system.actorOf(HmdaQuery.props, HmdaQuery.name)
  }

}

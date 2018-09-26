package hmda.persistence.submission

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{
  ActorContext,
  ActorSystem,
  Behavior,
  Props,
  SupervisorStrategy
}
import akka.cluster.sharding.typed.ClusterShardingSettings
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, EntityTypeKey}
import akka.persistence.typed.scaladsl.{Effect, PersistentBehaviors}
import akka.persistence.typed.scaladsl.PersistentBehaviors.CommandHandler
import com.typesafe.config.ConfigFactory
import hmda.messages.submission.SubmissionStats
import hmda.messages.submission.SubmissionStatsCommands._
import hmda.model.filing.submission.FilingId
import hmda.persistence.HmdaPersistence.config

object SubmissionStatsPersistence {

  case class SubmissionStatsState(
      submissionStats: Set[SubmissionStats] = Set.empty) {
    def updated(event: SubmissionStatsEvent): SubmissionStatsState = {
      event match {
        case SubmissionAdded(submissionId) =>
          if (!submissionStats
                .map(s => s.submissionId)
                .contains(submissionId)) {
            val updatedSet
              : Set[SubmissionStats] = submissionStats + SubmissionStats(
              submissionId)
            SubmissionStatsState(updatedSet)
          } else {
            this
          }

      }
    }
  }

  final val name = "SubmissionStats"

  val ShardingTypeName = EntityTypeKey[SubmissionStatsCommand](name)

  def behavior(filingId: String) =
    PersistentBehaviors
      .receive[SubmissionStatsCommand,
               SubmissionStatsEvent,
               SubmissionStatsState](
        persistenceId = s"$name-$filingId",
        emptyState = SubmissionStatsState(),
        commandHandler = commandHandler,
        eventHandler = eventHandler
      )
      .snapshotEvery(1000)

  val commandHandler: CommandHandler[SubmissionStatsCommand,
                                     SubmissionStatsEvent,
                                     SubmissionStatsState] = {
    (ctx, state, cmd) =>
      cmd match {
        case AddSubmission(submissionId) =>
          if (state.submissionStats
                .map(s => s.submissionId)
                .contains(submissionId)) {
            Effect.none
          } else {
            Effect.persist(SubmissionAdded(submissionId)).thenRun { _ =>
              ctx.log.debug(s"Add submission: $submissionId")
            }
          }

      }

  }

  val eventHandler
    : (SubmissionStatsState, SubmissionStatsEvent) => SubmissionStatsState = {
    case (state, event) => state.updated(event)
  }

  def startSubmissionStatsSharding(system: ActorSystem[_],
                                   entityId: FilingId): Unit = {
    val typeKey = ShardingTypeName
    val config = ConfigFactory.load()
    val shardNumber = config.getInt("hmda.submissionStats.shardNumber")
    val sharding = ClusterSharding(system)
    sharding.spawn(
      entityId => behavior(entityId),
      Props.empty,
      typeKey,
      ClusterShardingSettings(system),
      maxNumberOfShards = shardNumber,
      handOffStopMessage = SubmissionStatsStop
    )
  }

}

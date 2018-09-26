package hmda.persistence.submission

import java.time.Instant

import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.persistence.typed.scaladsl.PersistentBehaviors.CommandHandler
import akka.persistence.typed.scaladsl.{Effect, PersistentBehaviors}
import hmda.messages.submission.SubmissionCommands.{
  CreateSubmission,
  GetSubmission,
  ModifySubmission,
  SubmissionCommand
}
import hmda.messages.submission.SubmissionEvents.{
  SubmissionCreated,
  SubmissionEvent,
  SubmissionModified,
  SubmissionNotExists
}
import hmda.messages.submission.SubmissionStatsCommands.AddSubmission
import hmda.model.filing.submission.{Created, Submission, SubmissionId}

object SubmissionPersistence {

  case class SubmissionState(submission: Option[Submission])

  final val name = "Submission"

  def behavior(submissionId: SubmissionId) =
    PersistentBehaviors
      .receive[SubmissionCommand, SubmissionEvent, SubmissionState](
        persistenceId = s"$name-${submissionId.toString}",
        emptyState = SubmissionState(None),
        commandHandler = commandHandler,
        eventHandler = eventHandler
      )
      .snapshotEvery(1000)
      .withTagger(_ => Set(s"$name-${submissionId.lei}"))

  val commandHandler
    : CommandHandler[SubmissionCommand, SubmissionEvent, SubmissionState] = {
    (ctx, state, cmd) =>
      val sharding = ClusterSharding(ctx.system)
      cmd match {
        case GetSubmission(replyTo) =>
          replyTo ! state.submission
          Effect.none
        case CreateSubmission(submissionId, replyTo) =>
          val submission = Submission(
            submissionId,
            Created,
            Instant.now().toEpochMilli
          )
          val submissionStatsPersistence = sharding.entityRefFor(
            SubmissionStatsPersistence.ShardingTypeName,
            s"${SubmissionStatsPersistence.name}-${submissionId.lei}-${submissionId.period}"
          )
          Effect.persist(SubmissionCreated(submission)).thenRun { _ =>
            ctx.log.debug(
              s"persisted new Submission: ${submission.id.toString}")
            submissionStatsPersistence ! AddSubmission(submission.id)
            replyTo ! SubmissionCreated(submission)
          }
        case ModifySubmission(submission, replyTo) =>
          if (state.submission.map(s => s.id).contains(submission.id)) {
            Effect.persist(SubmissionModified(submission)).thenRun { _ =>
              ctx.log.debug(
                s"persisted modified Submission: ${submission.toString}")
              replyTo ! SubmissionModified(submission)
            }
          } else {
            replyTo ! SubmissionNotExists(submission.id)
            Effect.none
          }
      }
  }

  val eventHandler: (SubmissionState, SubmissionEvent) => SubmissionState = {
    case (state, SubmissionCreated(submission)) => state.copy(Some(submission))
    case (state, SubmissionModified(submission)) =>
      if (state.submission.getOrElse(Submission()).id == submission.id) {
        SubmissionState(Some(submission))
      } else {
        state
      }
    case (state, SubmissionNotExists(_)) => state
  }

}

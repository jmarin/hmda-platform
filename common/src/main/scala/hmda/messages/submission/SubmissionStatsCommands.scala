package hmda.messages.submission

import hmda.messages.CommonMessages.{Command, Event}
import hmda.model.filing.submission.SubmissionId

object SubmissionStatsCommands {

  sealed trait SubmissionStatsCommand extends Command
  sealed trait SubmissionStatsEvent extends Event

  case class AddSubmission(submissionId: SubmissionId)
      extends SubmissionStatsCommand

  case object SubmissionStatsStop extends SubmissionStatsCommand

  case class SubmissionAdded(submissionId: SubmissionId)
      extends SubmissionStatsEvent

}

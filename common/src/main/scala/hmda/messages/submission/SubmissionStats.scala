package hmda.messages.submission

import hmda.model.filing.submission.SubmissionId

case class SubmissionStats(submissionId: SubmissionId = SubmissionId(),
                           totalUploaded: Int = 0,
                           totalParsed: Int = 0,
                           totalValidated: Int = 0)

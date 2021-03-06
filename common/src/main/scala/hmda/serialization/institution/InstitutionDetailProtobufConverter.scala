package hmda.serialization.institution

import hmda.persistence.serialization.institution.institutiondetail.InstitutionDetailMessage
import hmda.serialization.filing.FilingProtobufConverter._
import InstitutionProtobufConverter._
import hmda.model.institution.InstitutionDetail
import hmda.persistence.serialization.institution.InstitutionMessage
object InstitutionDetailProtobufConverter {

  def institutionDetailToProtobuf(
      institutionDetail: InstitutionDetail): InstitutionDetailMessage = {
    InstitutionDetailMessage(
      if (institutionDetail.institution.isEmpty) None
      else Some(institutionToProtobuf(institutionDetail.institution.get)),
      institutionDetail.filings.map(s => filingToProtobuf(s))
    )
  }

  def institutionDetailFromProtobuf(
      institutionDetailMessage: InstitutionDetailMessage): InstitutionDetail = {
    InstitutionDetail(
      institution = Some(institutionFromProtobuf(
        institutionDetailMessage.institution.getOrElse(InstitutionMessage()))),
      filings = institutionDetailMessage.filings
        .map(s => filingFromProtobuf(s))
        .toList
    )
  }

}

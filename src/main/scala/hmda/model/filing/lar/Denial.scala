package hmda.model.filing.lar

import hmda.model.filing.lar.enums.{DenialReasonEnum, DenialReasonNotApplicable}

case class Denial(
    denialReason1: DenialReasonEnum,
    denialReason2: DenialReasonEnum,
    denialReason3: DenialReasonEnum,
    denialReason4: DenialReasonEnum,
    otherDenialReason: String
)

object Denial {
  def empty: Denial = {
    Denial(DenialReasonNotApplicable,
           DenialReasonNotApplicable,
           DenialReasonNotApplicable,
           DenialReasonNotApplicable,
           "")
  }
}

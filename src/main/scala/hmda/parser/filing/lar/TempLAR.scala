package hmda.parser.filing.lar

import hmda.model.filing.lar.{Geography, LarAction, LarIdentifier, Loan}

case class TempLAR(
    larIdentifier: LarIdentifier,
    action: LarAction
)

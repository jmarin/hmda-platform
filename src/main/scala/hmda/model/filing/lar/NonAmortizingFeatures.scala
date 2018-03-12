package hmda.model.filing.lar

import hmda.model.filing.lar.enums._

case class NonAmortizingFeatures(
    balloonPayment: BalloonPaymentEnum,
    interestOnlyPayments: InterestOnlyPaymentsEnum,
    negativeAmortization: NegativeAmortizationEnum,
    otherNonAmortizingFeatures: OtherNonAmortizingFeaturesEnum)

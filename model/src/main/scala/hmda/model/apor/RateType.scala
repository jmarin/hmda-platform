package hmda.model.apor

sealed trait RateType
case object FixedRate extends RateType
case object VariableRate extends RateType

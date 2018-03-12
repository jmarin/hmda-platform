package hmda.model.filing.lar

import hmda.model.filing.lar.enums.{
  ManufacturedHomeLandPropertyInterestEnum,
  ManufacturedHomeSecuredPropertyEnum
}

case class Property(
    propertyValue: String,
    manufacturedHomeSecuredProperty: Option[
      ManufacturedHomeSecuredPropertyEnum] = None,
    manufacturedHomeLandPropertyInterestEnum: Option[
      ManufacturedHomeLandPropertyInterestEnum] = None,
    totalUnits: Option[Int] = None,
    multiFamilyAffordableUnits: Option[String] = None
)

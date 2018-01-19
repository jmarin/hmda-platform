package hmda.model.filing.ts.validation

object TsValidationErrorModel {

  sealed trait TsDomainModelValidation {
    def errorMessage: String
  }

  case object TsIdentifierIsNotOneModel extends TsDomainModelValidation {
    override def errorMessage: String =
      "Transmittal Sheet identifier must be equal to 1"
  }

  case object TsInvalidAgencyCodeModel extends TsDomainModelValidation {
    override def errorMessage: String = "Agency Code must be 1,2,3,5,7, or 9"
  }

  case object TsInvalidStateCodeModel extends TsDomainModelValidation {
    override def errorMessage: String = "State abbreviation code is not valid"
  }

  case object TsInvalidZipCodeFormatModel extends TsDomainModelValidation {
    override def errorMessage: String = "Zip Code format is not valid"
  }

}

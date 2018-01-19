package hmda.model.filing.ts.validation

object TsValidationErrorModel {

  sealed trait TsParserDomainValidation {
    def errorMessage: String
  }

  case object TsIdentifierIsNotOne extends TsParserDomainValidation {
    override def errorMessage: String =
      "Transmittal Sheet identifier must be equal to 1"
  }

  case object TsAgencyCodeNotRecognized extends TsParserDomainValidation {
    override def errorMessage: String = "Agency Code must be 1,2,3,5,7, or 9"
  }

  case object TsStateCodeNotRecognized extends TsParserDomainValidation {
    override def errorMessage: String = "State abbreviation code is invalid"
  }

}

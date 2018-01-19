package hmda.model.filing.ts.validation

import cats.implicits._
import cats.data.ValidatedNel
import hmda.model.census.Census
import hmda.model.filing.ts.Address
import hmda.model.filing.ts.validation.TsValidationErrorModel._
import hmda.model.institution.Agency
import hmda.model.regex.CommonRegEx._

sealed trait TsModelValidator {

  type TsValidationResult[A] = ValidatedNel[TsDomainModelValidation, A]

  def validateId(id: Int): TsValidationResult[Int] = {
    if (id == 1)
      id.validNel
    else
      TsIdentifierIsNotOneModel.invalidNel
  }

  def validateAgencyCode(id: Int): TsValidationResult[Int] = {
    if (Agency.values.contains(id))
      id.validNel
    else
      TsInvalidAgencyCodeModel.invalidNel
  }

  private def validateStr(str: String): TsValidationResult[String] = {
    str.validNel
  }

  private def validateState(state: String): TsValidationResult[String] = {
    if (Census.states.contains(state)) {
      state.validNel
    } else {
      TsInvalidStateCodeModel.invalidNel
    }
  }

  private def validateZipCode(zipCode: String): TsValidationResult[String] = {
    if (zipCode.matches(validZipCodeRegEx)) {
      zipCode.validNel
    } else {
      TsInvalidZipCodeFormatModel.invalidNel
    }
  }

  def validateAddress(address: Address): TsValidationResult[Address] = {
    (validateStr(address.street),
     validateStr(address.city),
     validateState(address.state),
     validateZipCode(address.zipCode)).mapN(Address)
  }

  //  def validateTS(ts: TransmittalSheet): TsValidationResult[TransmittalSheet] = {
  //    (
  //      validateId(ts.id),
  //      validateAgencyCode(ts.agency.value)
  //    ).mapN(TransmittalSheet)
  //  }
}

object TsModelValidator extends TsModelValidator

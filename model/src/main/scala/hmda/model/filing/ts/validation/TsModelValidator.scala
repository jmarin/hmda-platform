package hmda.model.filing.ts.validation

import cats.implicits._
import cats.data.ValidatedNel
import hmda.model.census.Census
import hmda.model.filing.ts.Address
import hmda.model.filing.ts.validation.TsValidationErrorModel.{
  TsAgencyCodeNotRecognized,
  TsIdentifierIsNotOne,
  TsParserDomainValidation,
  TsStateCodeNotRecognized
}
import hmda.model.institution.Agency

sealed trait TsModelValidator {

  type TsValidationResult[A] = ValidatedNel[TsParserDomainValidation, A]

  def validateId(id: Int): TsValidationResult[Int] = {
    if (id == 1)
      id.validNel
    else
      TsIdentifierIsNotOne.invalidNel
  }

  def validateAgencyCode(id: Int): TsValidationResult[Int] = {
    if (Agency.values.contains(id))
      id.validNel
    else
      TsAgencyCodeNotRecognized.invalidNel
  }

  def validateStr(str: String): TsValidationResult[String] = {
    str.validNel
  }

  def validateState(state: String): TsValidationResult[String] = {
    if (Census.states.contains(state)) {
      state.validNel
    } else {
      TsStateCodeNotRecognized.invalidNel
    }
  }

  def validateAddress(address: Address): TsValidationResult[Address] = {
    (validateStr(address.street),
     validateStr(address.city),
     validateState(address.state),
     //TODO: perform validation for zipCode format
     validateStr(address.zipCode)).mapN(Address)
  }

  //  def validateTS(ts: TransmittalSheet): TsValidationResult[TransmittalSheet] = {
  //    (
  //      validateId(ts.id),
  //      validateAgencyCode(ts.agency.value)
  //    ).mapN(TransmittalSheet)
  //  }
}

object TsModelValidator extends TsModelValidator

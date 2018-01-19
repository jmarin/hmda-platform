package hmda.model.regex

object CommonRegEx {

  val validZipCodeRegEx = "^\\d{5}(?:-\\d{4})?$"
  val validPhoneNumber = "^\\d{3}-\\d{3}-\\d{4}$"
  val validEmail =
    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
  val validTaxId = "^\\d{2}-\\d{7}$"

}

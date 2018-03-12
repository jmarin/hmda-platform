package hmda.parser

object ParserErrorModel {

  def notAnInteger(fieldName: String) = s"$fieldName is not an integer"

  def notNumeric(fieldName: String) = s"$fieldName is not numeric"

  def notStringOrNA(fieldName: String) =
    s"$fieldName is not a non-empty string or NA"

  trait ParserValidationError {
    def errorMessage: String
  }

  case class IncorrectNumberOfFields(length: Int, expectedLength: Int)
      extends ParserValidationError {
    override def errorMessage: String =
      s"An incorrect number of data fields were reported: $length data fields were found, when $expectedLength data fields were expected."
  }

}

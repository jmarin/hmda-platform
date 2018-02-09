package hmda.api.protocol.fi.ts

import hmda.model.fi.ts.{ Contact, Parent, Respondent, TransmittalSheet }
import spray.json.DefaultJsonProtocol

trait TsProtocol extends DefaultJsonProtocol {

  implicit val respondentFormat = jsonFormat6(Respondent.apply)
  implicit val contactFormat = jsonFormat4(Contact.apply)
  implicit val parentFormat = jsonFormat5(Parent.apply)
  implicit val transmittalSheetFormat = jsonFormat9(TransmittalSheet.apply)

}

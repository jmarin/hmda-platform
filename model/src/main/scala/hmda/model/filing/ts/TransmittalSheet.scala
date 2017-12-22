package hmda.model.filing.ts

import hmda.model.filing.HmdaFileRow
import hmda.model.institution.{Agency, UndeterminedAgency}

case class TransmittalSheet(
    id: Int = 1,
    institutionName: String = "",
    year: Int = 2018,
    quarter: Int = 1,
    contact: Contact = Contact(),
    agency: Agency = UndeterminedAgency,
    totalLines: Int = 0,
    taxId: String = "",
    LEI: String = ""
) extends HmdaFileRow {
  def toCSV: String = {
    s"$id|$institutionName|$year|$quarter|${contact.toCSV}|${agency.value}|$totalLines|$taxId|$LEI"
  }

  override def valueOf(field: String): Any = {
    TsFieldMapping.mapping(this).getOrElse(field, "error: field name mismatch")
  }
}

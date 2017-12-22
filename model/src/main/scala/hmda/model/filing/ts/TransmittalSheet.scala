package hmda.model.filing.ts

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
) {
  def toCSV: String = {
    s"$id|$institutionName|$year|$quarter|${contact.toCSV}|${agency.value}|$totalLines|$taxId|$LEI"
  }
}

package hmda.model.filing.lar.enums

sealed trait OccupancyEnum extends LarEnum

object OccupancyEnum extends LarCodeEnum[OccupancyEnum] {
  override val values = List(1, 2, 3)

  override def valueOf(code: Int): OccupancyEnum = {
    code match {
      case 1 => PrincipalResidence
      case 2 => SecondResidence
      case 3 => InvestmentProperty
      case _ => InvalidOccupancyCode
    }
  }
}

case object PrincipalResidence extends OccupancyEnum {
  override val code: Int = 1
  override val description: String = "Principal Residence"
}

case object SecondResidence extends OccupancyEnum {
  override val code: Int = 2
  override val description: String = "Second Residence"
}

case object InvestmentProperty extends OccupancyEnum {
  override val code: Int = 3
  override val description: String = "Investment Property"
}

case object InvalidOccupancyCode extends OccupancyEnum {
  override def code: Int = -1
  override def description: String = "Invalid Code"
}

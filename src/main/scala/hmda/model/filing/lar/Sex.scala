package hmda.model.filing.lar

import hmda.model.filing.lar.enums.{
  SexEnum,
  SexNotApplicable,
  SexObservedEnum,
  SexObservedNotApplicable
}

case class Sex(sexEnum: SexEnum, sexObservedEnum: SexObservedEnum)

object Sex {
  def empty: Sex = {
    Sex(SexNotApplicable, SexObservedNotApplicable)
  }
}

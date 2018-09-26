package hmda.model.filing.submission

case class FilingId(lei: String = "", period: String = "") {
  override def toString: String = s"$lei-$period"

  def isEmpty: Boolean = {
    lei == "" && period == ""
  }
}

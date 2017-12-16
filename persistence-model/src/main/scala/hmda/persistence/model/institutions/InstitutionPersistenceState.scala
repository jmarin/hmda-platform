package hmda.persistence.model.institutions

import hmda.model.institution.Institution
import hmda.persistence.messages.CommonMessages.Event
import hmda.persistence.messages.events.institutions.InstitutionEvents.{ InstitutionCreated, InstitutionModified }

case class InstitutionPersistenceState(institutions: Set[Institution] = Set.empty[Institution]) {
  def updated(event: Event): InstitutionPersistenceState = {
    event match {
      case InstitutionCreated(i) =>
        InstitutionPersistenceState(institutions + i)
      case InstitutionModified(i) =>
        val elem = institutions.find(x => x.id == i.id).getOrElse(Institution.empty)
        val updated = (institutions - elem) + i
        InstitutionPersistenceState(updated)

    }
  }
}

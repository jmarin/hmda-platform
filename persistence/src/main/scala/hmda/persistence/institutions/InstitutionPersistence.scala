package hmda.persistence.institutions

import akka.actor.{ ActorRef, ActorSystem, Props }
import akka.persistence.{ SaveSnapshotFailure, SaveSnapshotSuccess, SnapshotOffer }
import com.typesafe.config.ConfigFactory
import hmda.model.institution.Institution
import hmda.persistence.institutions.InstitutionPersistence._
import hmda.persistence.messages.CommonMessages._
import hmda.persistence.messages.commands.institutions.InstitutionCommands._
import hmda.persistence.messages.events.institutions.InstitutionEvents.{ InstitutionCreated, InstitutionModified }
import hmda.persistence.model.HmdaPersistentActor
import hmda.persistence.model.institutions.InstitutionPersistenceState

import scala.concurrent.duration._

object InstitutionPersistence {

  case object SaveState

  val name = "institutions"

  def props: Props = Props(new InstitutionPersistence)

  def createInstitutions(system: ActorSystem): ActorRef = {
    system.actorOf(InstitutionPersistence.props.withDispatcher("persistence-dispatcher"), name)
  }
}

class InstitutionPersistence extends HmdaPersistentActor {

  context.system.scheduler.scheduleOnce(120.seconds, self, SaveState)

  val config = ConfigFactory.load()

  val snapshotInterval = config.getInt("hmda.journal.snapshot.counter")

  var state = InstitutionPersistenceState()

  override def updateState(event: Event): Unit = {
    state = state.updated(event)
  }

  override def persistenceId: String = s"$name"

  override def receiveCommand: Receive = {
    case CreateInstitution(i) =>
      if (!state.institutions.map(x => x.id).contains(i.id)) {
        persist(InstitutionCreated(i)) { e =>
          log.debug(s"Persisted: $i")
          updateState(e)
          saveState()
          sender() ! Some(e.institution)
        }
      } else {
        sender() ! None
        log.warning(s"Institution already exists. Could not create $i")
      }

    case ModifyInstitution(i) =>
      if (state.institutions.map(x => x.id).contains(i.id)) {
        persist(InstitutionModified(i)) { e =>
          log.debug(s"Modified: ${i.respondent.name}")
          updateState(e)
          saveState()
          sender() ! Some(e.institution)
        }
      } else {
        sender() ! None
        log.warning(s"Institution does not exist. Could not update $i")
      }

    case GetInstitutionById(id) =>
      val i = state.institutions.find(x => x.id == id)
      sender() ! i

    case GetInstitutionsById(ids) =>
      val institutions = state.institutions.filter(i => ids.contains(i.id))
      sender() ! institutions

    case GetInstitutionByRespondentId(respondentId) =>
      val institution = state.institutions.find { i =>
        i.respondent.externalId.value == respondentId
      }.getOrElse(Institution.empty)
      sender() ! institution

    case FindInstitutionByDomain(domain) =>
      if (domain.isEmpty) {
        sender() ! Set.empty[Institution]
      } else {
        sender() ! state.institutions.filter(i => i.emailDomains.map(e => extractDomain(e)).contains(domain.toLowerCase))
      }

    case GetState =>
      sender() ! state.institutions

    case SaveState =>
      println("Sending SaveState message")
      saveSnapshot(state)

    case Shutdown => context stop self

    case SaveSnapshotSuccess(metadata) =>
      log.info(s"saved snapshot for ${metadata.persistenceId}")

    case SaveSnapshotFailure(metadata, reason) =>
      log.info(s"Could not save snapshot for ${metadata.persistenceId} because ${reason.getLocalizedMessage}")
  }

  private def saveState(): Unit = {
    println(s"$lastSequenceNr, $snapshotInterval")
    if (lastSequenceNr % snapshotInterval == 0 && lastSequenceNr != 0) {
      println("SAVING STATE")
      saveSnapshot(state)
    }
  }

  override def receiveRecover: Receive = {
    case SnapshotOffer(_, s: InstitutionPersistenceState) =>
      log.debug(s"Recovering state from snapshot for ${InstitutionPersistence.name}")
      state = s
    case event: Event =>
      updateState(event)
  }

  private def extractDomain(email: String): String = {
    val parts = email.toLowerCase.split("@")
    if (parts.length > 1)
      parts(1)
    else
      parts(0)
  }

}

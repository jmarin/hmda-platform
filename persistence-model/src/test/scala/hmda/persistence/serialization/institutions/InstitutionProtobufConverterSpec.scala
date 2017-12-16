package hmda.persistence.serialization.institutions

import hmda.model.institution.InstitutionGenerators._
import hmda.persistence.messages.commands.institutions.InstitutionCommands._
import hmda.persistence.messages.events.institutions.InstitutionEvents.{ InstitutionCreated, InstitutionModified }
import hmda.persistence.model.institutions.InstitutionPersistenceState
import hmda.persistence.model.serialization.InstitutionCommands._
import hmda.persistence.model.serialization.InstitutionEvents._
import hmda.persistence.serialization.institutions.InstitutionProtobufConverter._
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{ MustMatchers, PropSpec }

class InstitutionProtobufConverterSpec extends PropSpec with PropertyChecks with MustMatchers {
  property("Top Holder must serialize to protobuf and back") {
    forAll(topHolderGen) { topHolder =>
      val protobuf = topHolderToProtobuf(topHolder).toByteArray
      topHolderFromProtobuf(TopHolderMessage.parseFrom(protobuf)) mustBe topHolder
    }
  }

  property("Parent must serialize to protobuf and back") {
    forAll(parentGen) { parent =>
      val protobuf = parentToProtobuf(parent).toByteArray
      parentFromProtobuf(ParentMessage.parseFrom(protobuf)) mustBe parent
    }
  }

  property("Respondent must serialize to protobuf and back") {
    forAll(respondentGen) { respondent =>
      val bytes = respondentToProtobuf(respondent).toByteArray
      respondentFromProtobuf(RespondentMessage.parseFrom(bytes)) mustBe respondent
    }
  }

  property("External Id Type must serialize to protobuf and back") {
    forAll(externalIdTypeGen) { externalIdType =>
      val protobuf = externalIdTypeToProtobuf(externalIdType)
      externalIdTypeFromProtobuf(protobuf) mustBe externalIdType
    }
  }

  property("External Id must serialize to protobuf and back") {
    forAll(externalIdGen) { externalId =>
      val protobuf = externalIdToProtobuf(externalId).toByteArray
      externalIdFromProtobuf(ExternalIdMessage.parseFrom(protobuf)) mustBe externalId
    }
  }

  property("Institution Type must serialize to protobuf and back") {
    forAll(institutionTypeGen) { institutionType =>
      val protobuf = institutionTypeToProtobuf(institutionType)
      institutionTypeFromProtobuf(protobuf) mustBe institutionType
    }
  }

  property("Agency must serialize to protobuf and back") {
    forAll(agencyGen) { agency =>
      val protobuf = agencyToProtobuf(agency)
      agencyFromProtobuf(protobuf) mustBe agency
    }
  }

  property("Institution must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = institutionToProtobuf(institution).toByteArray
      institutionFromProtobuf(InstitutionMessage.parseFrom(protobuf)) mustBe institution
    }
  }

  property("InstitutionCreated must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = institutionCreatedToProtobuf(InstitutionCreated(institution)).toByteArray
      institutionCreatedFromProtobuf(InstitutionCreatedMessage.parseFrom(protobuf)) mustBe InstitutionCreated(institution)
    }
  }

  property("InstitutionModified must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = institutionModifiedToProtobuf(InstitutionModified(institution)).toByteArray
      institutionModifiedFromProtobuf(InstitutionModifiedMessage.parseFrom(protobuf)) mustBe InstitutionModified(institution)
    }
  }

  property("Create Institution must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = createInstitutionToProtobuf(CreateInstitution(institution)).toByteArray
      createInstitutionFromProtobuf(CreateInstitutionMessage.parseFrom(protobuf)) mustBe CreateInstitution(institution)
    }
  }

  property("Modify Institution must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = modifyInstitutionToProtobuf(ModifyInstitution(institution)).toByteArray
      modifyInstitutionFromProtobuf(ModifyInstitutionMessage.parseFrom(protobuf)) mustBe ModifyInstitution(institution)
    }
  }

  property("Get Institution By Respondent ID must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = getInstitutionByRespondentIdToProtobuf(GetInstitutionByRespondentId(institution.id)).toByteArray
      getInstitutionByRespondentIdFromProtobuf(GetInstitutionByRespondentIdMessage.parseFrom(protobuf)) mustBe GetInstitutionByRespondentId(institution.id)
    }
  }

  property("Get Institution By ID must serialize to protobuf and back") {
    forAll(institutionGen) { institution =>
      val protobuf = getInstitutionByIdToProtobuf(GetInstitutionById(institution.id)).toByteArray
      getInstitutionByIdFromProtobuf(GetInstitutionByIdMessage.parseFrom(protobuf)) mustBe GetInstitutionById(institution.id)
    }
  }

  property("Get Institutions By ID must serialize to protobuf and back") {
    forAll(Gen.listOfN(5, Gen.alphaStr)) { list =>
      val protobuf = getInstitutionsByIdToProtobuf(GetInstitutionsById(list)).toByteArray
      getInstitutionsByIdFromProtobuf(GetInstitutionsByIdMessage.parseFrom(protobuf)) mustBe GetInstitutionsById(list)
    }
  }

  property("Find Institutions By Domain must serialize to protobuf and back") {
    forAll(Gen.alphaStr) { domain =>
      val protobuf = findInstitutionByDomainToProtobuf(FindInstitutionByDomain(domain)).toByteArray
      findInstitutionByDomainFromProtobuf(FindInstitutionByDomainMessage.parseFrom(protobuf)) mustBe FindInstitutionByDomain(domain)
    }
  }

  property("Institution Persistence State must serialize to protobuf and back") {
    forAll(institutionNGen(100)) { institutions =>
      val institutionSet = institutions.toSet
      val protobuf = institutionPersistenceStateToProtobuf(InstitutionPersistenceState(institutionSet)).toByteArray
      institutionPersistenceStateFromProtobuf(InstitutionPersistenceStateMessage.parseFrom(protobuf)).institutions mustBe institutionSet
    }
  }

}

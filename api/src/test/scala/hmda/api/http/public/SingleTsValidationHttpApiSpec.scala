package hmda.api.http.public

import akka.event.{ LoggingAdapter, NoLogging }
import akka.http.scaladsl.testkit.{ RouteTestTimeout, ScalatestRouteTest }
import akka.util.Timeout
import hmda.model.fi.ts.TransmittalSheet
import hmda.parser.fi.ts.TsCsvParser
import hmda.persistence.HmdaSupervisor
import hmda.validation.stats.ValidationStats
import org.scalatest.{ MustMatchers, WordSpec }
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import spray.json._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

class SingleTsValidationHttpApiSpec extends WordSpec with MustMatchers with ScalatestRouteTest
    with SingleTsValidationHttpApi {
  override val log: LoggingAdapter = NoLogging
  override implicit val timeout: Timeout = Timeout(5.seconds)

  val ec: ExecutionContext = system.dispatcher

  implicit val routeTestTimeout: RouteTestTimeout = RouteTestTimeout(5.seconds)

  //Start up API Actors
  val validationStats = ValidationStats.createValidationStats(system)
  val supervisor = HmdaSupervisor.createSupervisor(system, validationStats)

  val validTs = "1|0123456789|9|201301171330|2013|99-9999999|900|MIKES SMALL BANK   XXXXXXXXXXX|1234 Main St       XXXXXXXXXXXXXXXXXXXXX|Sacramento         XXXXXX|CA|99999-9999|MIKES SMALL INC    XXXXXXXXXXX|1234 Kearney St    XXXXXXXXXXXXXXXXXXXXX|San Francisco      XXXXXX|CA|99999-1234|Mrs. Krabappel     XXXXXXXXXXX|916-999-9999|999-753-9999|krabappel@gmail.comXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  val ts = TsCsvParser(validTs).right.getOrElse(TransmittalSheet())
  val tsJson = ts.toJson

  "TS HTTP Service" must {
    "parse a valid pipe delimited TS and return JSON representation" in {
      Post("/ts/parse", validTs) ~> tsRoutes(supervisor) ~> check {
        status mustEqual StatusCodes.OK
        responseAs[TransmittalSheet] mustBe ts
      }
    }
  }

}

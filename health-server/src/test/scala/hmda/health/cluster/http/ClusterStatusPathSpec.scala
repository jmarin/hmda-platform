package hmda.health.cluster.http

import akka.http.scaladsl.model.ws.BinaryMessage
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import akka.util.ByteString
import org.scalatest.{MustMatchers, WordSpec}
import scala.concurrent.duration._

class ClusterStatusPathSpec
    extends WordSpec
    with MustMatchers
    with ScalatestRouteTest
    with ClusterStatusPaths {

  val wsClient = WSProbe()

  "Check WS" in {
    WS("/cluster/status", wsClient.flow) ~> clusterStatusRoutes ~>
      check {
        // check response for WS Upgrade headers
        isWebSocketUpgrade mustBe true

        // manually run a WS conversation
        wsClient.sendMessage("Peter")
        wsClient.expectMessage("Hello Peter!")

        wsClient.sendMessage(BinaryMessage(ByteString("abcdef")))
        wsClient.expectNoMessage(100.millis)

        wsClient.sendMessage("John")
        wsClient.expectMessage("Hello John!")

        wsClient.sendCompletion()
        wsClient.expectCompletion()
      }
  }
}

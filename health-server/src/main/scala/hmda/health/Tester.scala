package hmda.health

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.stream.scaladsl.{
  BroadcastHub,
  Flow,
  Keep,
  RunnableGraph,
  Sink,
  Source
}

import scala.collection.immutable.Seq
import scala.concurrent.duration._

object Tester extends App {

  implicit val system = ActorSystem("Server")
  implicit val mat = ActorMaterializer()

  // The source to broadcast (just ints for simplicity)
  private val dataSource = Source(1 to 1000)
    .throttle(1, 1.second, 1, ThrottleMode.Shaping)
    .map(_.toString)

  // Go via BroadcastHub to allow multiple clients to connect
  val runnableGraph: RunnableGraph[Source[String, NotUsed]] =
    dataSource.toMat(BroadcastHub.sink(bufferSize = 256))(Keep.right)

  val producer: Source[String, NotUsed] = runnableGraph.run()

  // Optional - add sink to avoid backpressuring the original flow when no clients are attached
  producer.runWith(Sink.ignore)

  private val wsHandler: Flow[Message, Message, NotUsed] =
    Flow[Message]
      .mapConcat(_ ⇒ Seq.empty[String]) // Ignore any data sent from the client
      .merge(producer) // Stream the data we want to the client
      .map(l => TextMessage(l.toString))

  val route =
    path("ws") {
      handleWebSocketMessages(wsHandler)
    }

  val port = 8080

  println("Starting up route")
  Http().bindAndHandle(route, "0.0.0.0", port)
  println(s"Started HTTP server on port $port")

}

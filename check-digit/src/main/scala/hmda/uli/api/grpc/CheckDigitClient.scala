package hmda.uli.api.grpc

import com.typesafe.config.ConfigFactory
import hmda.proto.uli.{CheckDigitRequest, CheckDigitServiceGrpc}
import io.grpc.ManagedChannelBuilder

import scala.concurrent.Await
import scala.concurrent.duration._

object CheckDigitClient {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val port = config.getInt("hmda.uli.grpc.port")
    val channel = ManagedChannelBuilder
      .forAddress("localhost", port)
      .usePlaintext()
      .build()
    val request = CheckDigitRequest("10Bx939c5543TqA1144M999143X")
    val stub = CheckDigitServiceGrpc.stub(channel)
    val checkDigitF = stub.checkDigit(request)
    val response = Await.result(checkDigitF, 2.seconds)
    println(response)
  }

}

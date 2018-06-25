package hmda.uli.api.grpc

import com.typesafe.config.ConfigFactory
import hmda.proto.uli.{
  CheckDigitRequest,
  CheckDigitServiceGrpc,
  ValidateULIRequest
}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

import scala.concurrent.Await
import scala.concurrent.duration._

object CheckDigitClient {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val host = config.getString("hmda.uli.grpc.host")
    val port = config.getInt("hmda.uli.grpc.port")
    val channel = ManagedChannelBuilder
      .forAddress(host, port)
      .usePlaintext()
      .build()
    try {
      checkDigit(channel)
      validateUli(channel)
    } finally {
      channel.shutdown()
    }
  }

  private def checkDigit(channel: ManagedChannel): Unit = {
    try {
      val request = CheckDigitRequest("10Bx939c5543TqA1144M999143X")
      val stub = CheckDigitServiceGrpc.stub(channel)
      val checkDigitF = stub.checkDigit(request)
      val response = Await.result(checkDigitF, 2.seconds)
      println(response)
    } catch {
      case e: Exception =>
        println(e.getLocalizedMessage)
    }
  }

  private def validateUli(channel: ManagedChannel): Unit = {
    try {
      val request = ValidateULIRequest("10Bx939c5543TqA1144M999143X38")
      val stub = CheckDigitServiceGrpc.blockingStub(channel)
      val isValidResponse = stub.uliValidate(request)
      println(s"ULI valid: ${isValidResponse.isValid}")
    } catch {
      case e: Exception =>
        println(e.getLocalizedMessage)
    }
  }

}

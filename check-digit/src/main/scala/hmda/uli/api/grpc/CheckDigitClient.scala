package hmda.uli.api.grpc

import java.util.concurrent.{CountDownLatch, TimeUnit}

import com.typesafe.config.ConfigFactory
import hmda.proto.uli._
import io.grpc.stub.StreamObserver
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
      val finishLatch = checkDigitStream(channel)
      if (!finishLatch.await(1, TimeUnit.MINUTES))
        println("Check Digit Stream can not finish within 1 minutes")
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

  private def checkDigitStream(channel: ManagedChannel): CountDownLatch = {
    val finishLatch = new CountDownLatch(1)

    val responseObserver = new StreamObserver[CheckDigitResponse] {
      override def onNext(value: CheckDigitResponse): Unit = {
        println(value)
      }

      override def onCompleted(): Unit = {
        finishLatch.countDown()
      }

      override def onError(t: Throwable): Unit = {
        println(t.getLocalizedMessage)
        finishLatch.countDown()
      }
    }

    val stub = CheckDigitServiceGrpc.stub(channel)
    val requestObserver = stub.checkDigitStream(responseObserver)
    try {
      val requests = Seq(
        CheckDigitRequest("10Bx939c5543TqA1144M999143X"),
        CheckDigitRequest("10Cx939c5543TqA1144M999143X"),
        CheckDigitRequest("#%)WQD!")
      )
      for (request <- requests) {
        requestObserver.onNext(request)
      }

    } catch {
      case e: RuntimeException =>
        requestObserver.onError(e)
        throw e
    }
    requestObserver.onCompleted()
    finishLatch

  }

}

package hmda.uli.api.grpc

import com.typesafe.config.ConfigFactory
import hmda.gprc.GrpcServer
import hmda.proto.uli._
import hmda.uli.validation.ULI
import io.grpc.Status
import io.grpc.stub.StreamObserver

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object CheckDigitServer extends GrpcServer {

  class CheckDigitService extends CheckDigitServiceGrpc.CheckDigitService {

    override def checkDigit(
        request: CheckDigitRequest): Future[CheckDigitResponse] = {
      val loanId = request.loanId
      val maybeCheckDigit = Try(ULI.checkDigit(loanId))
      maybeCheckDigit match {
        case Success(digit) =>
          val uli = ULIResponse(loanId, digit, loanId + digit)
          val response = CheckDigitResponse(Some(uli))
          Future.successful(response)
        case Failure(error) =>
          Future.failed(
            Status.INTERNAL
              .augmentDescription(error.getLocalizedMessage)
              .asRuntimeException()
          )
      }
    }

    override def uliValidate(
        request: ValidateULIRequest): Future[ValidateULIResponse] = {
      val maybeIsValid = Try(ULI.validateULI(request.uli))
      maybeIsValid match {
        case Success(isValid) =>
          val response = ValidateULIResponse(isValid)
          Future.successful(response)
        case Failure(error) =>
          Future.failed(
            Status.INTERNAL
              .augmentDescription(error.getLocalizedMessage)
              .asRuntimeException()
          )
      }
    }

    override def checkDigitStream(
        responseObserver: StreamObserver[CheckDigitResponse])
      : StreamObserver[CheckDigitRequest] = {
      new StreamObserver[CheckDigitRequest] {

        var loanId: String = ""
        var digit: String = ""
        var checkDigit: String = ""

        override def onNext(value: CheckDigitRequest): Unit = {
          loanId = value.loanId
          println(s"checking: ${loanId}")
          val maybeCheckDigit = Try(ULI.checkDigit(loanId))
          maybeCheckDigit match {
            case Success(d) =>
              digit = d
              checkDigit = loanId + digit
              responseObserver.onNext(
                CheckDigitResponse(
                  Some(ULIResponse(loanId, digit, checkDigit))))
            case Failure(_) =>
          }
        }

        override def onCompleted(): Unit = {
          responseObserver.onCompleted()
        }

        override def onError(t: Throwable): Unit = {
          println(s"${t.getLocalizedMessage}")
        }

      }
    }
  }

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val port = config.getInt("hmda.uli.grpc.port")
    val serviceDefinition =
      CheckDigitServiceGrpc.bindService(new CheckDigitService,
                                        ExecutionContext.global)
    run(serviceDefinition, port)
  }

}

package helloworld

import com.example.protos.hello.{GreeterGrpc, HelloReply, HelloRequest}
import io.grpc.stub.StreamObserver

import scala.concurrent.{ExecutionContext, Future}

object HelloWorldServer extends GrpcServer {

  class HelloWorldService extends GreeterGrpc.Greeter {
    override def sayHello(request: HelloRequest): Future[HelloReply] = {
      Future.successful(HelloReply(message = s"Hello, ${request.name}"))
    }

    override def sayHelloStreaming(responseObserver: StreamObserver[HelloReply])
      : StreamObserver[HelloRequest] = ???
  }

  def main(args: Array[String]): Unit = {
    val ssd =
      GreeterGrpc.bindService(new HelloWorldService, ExecutionContext.global)
    runServer(ssd, 5000)
  }

}

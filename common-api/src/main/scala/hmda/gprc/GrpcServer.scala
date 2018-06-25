package hmda.gprc

import io.grpc.{ServerBuilder, ServerServiceDefinition}

trait GrpcServer {

  def run(serviceDefinition: ServerServiceDefinition, port: Int): Unit = {
    val server = ServerBuilder
      .forPort(port)
      .addService(serviceDefinition)
      .build
      .start

    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run(): Unit = server.shutdown()
    })

    server.awaitTermination()
  }

}

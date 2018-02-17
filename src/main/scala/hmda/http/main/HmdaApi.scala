package hmda.http.main

import akka.actor.Props
import hmda.model.actor.HmdaActor

object HmdaApi {
  final val name = "HmdaApi"
  def props: Props = Props(new HmdaApi)
}

class HmdaApi extends HmdaActor {

  val filingApi = context.actorOf(HmdaFilingApi.props)
  val adminApi = context.actorOf(HmdaAdminApi.props)
  val publicApi = context.actorOf(HmdaPublicApi.props)
  val metricsApi = context.actorOf(HmdaJvmMetricsApi.props)

}

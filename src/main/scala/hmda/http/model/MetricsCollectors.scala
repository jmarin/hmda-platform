package hmda.http.model

import io.prometheus.client.Histogram

object MetricsCollectors {

  val requestLatency =
    Histogram
      .build()
      .name(s"hmda_request_seconds")
      .help("Request latency in seconds")
      .labelNames("service", "method")
      .register()

}

package hmda.http.model

import io.prometheus.client.Histogram

object MetricsCollectors {

  val requestLatency =
    Histogram
      .build()
      .name(s"hmda_request_ms")
      .help("Request latency in ms")
      .labelNames("service", "method", "status")
      .buckets(0.10, 5, 15, 50, 100, 200, 300, 400, 500)
      .register()

}

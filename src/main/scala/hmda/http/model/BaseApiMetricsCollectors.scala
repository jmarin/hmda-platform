package hmda.http.model

import io.prometheus.client.{Gauge, Histogram}

object BaseApiMetricsCollectors {

  val filingApiRequestsInProgress =
    Gauge
      .build()
      .name("hmda_filing_base_api_requests_in_progress")
      .help("Requests In Progress")
      .register()

  val filingApiRequestLatency =
    Histogram
      .build()
      .name(s"hmda_filing_base_api_request_latency")
      .help("Request latency in seconds")
      .register()

  val adminApiRequestsInProgress =
    Gauge
      .build()
      .name("hmda_admin_base_api_requests_in_progress")
      .help("Requests In Progress")
      .register()

  val adminApiRequestLatency =
    Histogram
      .build()
      .name(s"hmda_admin_base_api_request_latency")
      .help("Request latency in seconds")
      .register()

  val publicApiRequestsInProgress =
    Gauge
      .build()
      .name("hmda_public_base_api_requests_in_progress")
      .help("Requests In Progress")
      .register()

  val publicApiRequestLatency =
    Histogram
      .build()
      .name(s"hmda_public_base_api_request_latency")
      .help("Request latency in seconds")
      .register()

  val jvmMetricsApiRequestsInProgress =
    Gauge
      .build()
      .name("hmda_jvm_metrics_api_requests_in_progress")
      .help("Requests In Progress")
      .register()

  val jvmMetricsApiRequestLatency =
    Histogram
      .build()
      .name(s"hmda_jvm_metrics_api_request_latency")
      .help("Request latency in seconds")
      .register()

}

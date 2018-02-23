from grafanalib.core import *

dashboard=Dashboard(
    title="HMDA Platform Service Statistics",
    timezone="browser",
    rows=[
        Row(panels=[
            Graph(
                title="Response Time - 95th Percentile (5m)",
                dataSource='Prometheus Monitor',
                description="Request duration within which 95% of requests are served",
                targets=[
                    Target(
                        expr='histogram_quantile(0.95, sum(rate(hmda_request_ms_bucket[5m])) by (service, method, status))',
                        legendFormat='',
                        refId='A'
                    )
                ]
            )
        ]),
        Row(panels=[
            Graph(
                title='Average Response Time (1m)',
                dataSource='Prometheus Monitor',
                description='Average Response Time',
                targets=[
                    Target(
                        expr='avg(rate(hmda_request_ms_sum[1m]) / rate(hmda_request_ms_count[1m])) by (service, method, status)',
                        legendFormat='{{service}} - {{method}} - {{status}}',
                        refId='A'
                    )
                ]
            )
        ])
    ]
).auto_panel_ids()
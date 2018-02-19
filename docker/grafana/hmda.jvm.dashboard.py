from grafanalib.core import *

dashboard = Dashboard(
    title = "HMDA Platform JVM Statistics",
    rows = [
        Row(panels=[
            Graph(
                title = "Memory Usage",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_memory_bytes_used',
                        legendFormat='{{instance}} - {{area}} - used',
                        refId='A'
                    ),
                    Target(
                        expr='jvm_memory_bytes_max',
                        legendFormat='{{instance}} - {{area}} - max',
                        refId='B'
                    )
                ],
                yAxes=[
                    YAxis(format=BYTES_FORMAT),
                    YAxis(format=SHORT_FORMAT)
                ]
            ),
            Graph(
                title = "JVM Memory Pool",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_memory_pool_bytes_used',
                        legendFormat='{{instance}} - {{pool}}',
                        refId='A'
                    )
                ],
                yAxes=[
                    YAxis(format=BYTES_FORMAT),
                    YAxis(format=SHORT_FORMAT)
                ]
            ),
        ]),
        Row(panels=[
            Graph(
                title = "GC Time",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_gc_collection_seconds_sum',
                        legendFormat='{{instance}} - {{gc}}',
                        refId='A'
                    )
                ],
                yAxes=[
                    YAxis(format=SECONDS_FORMAT),
                    YAxis(format=SHORT_FORMAT)
                ]
            ),
            Graph(
                title = "GC Count",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_gc_collection_seconds_count',
                        legendFormat='{{instance}} - {{gc}}',
                        refId='A'
                    )
                ]
            ),
        ]),
        Row(panels=[
            Graph(
                title = "Threads",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_threads_current',
                        legendFormat='{{instance}} - Current Threads',
                        refId='A'
                    ),
                    Target(
                        expr="jvm_threads_daemon",
                        legendFormat='{{instance}} - Daemon Threads',
                        refId='B'
                    )
                ]
            ),
            Graph(
                title = "Classes Loaded",
                dataSource='Prometheus Monitor',
                targets=[
                    Target(
                        expr='jvm_classes_loaded',
                        legendFormat='{{instance}} - currently loaded',
                        refId='A'
                    ),
                    Target(
                        expr='jvm_classes_loaded_total',
                        legendFormat='{{instance}} - total loaded',
                        refId='B'
                    ),
                    Target(
                        expr='jvm_classes_unloaded_total',
                        legendFormat='{{instance}} - total unloaded',
                        refId='C'
                    )
                ]
            ),
        ]),
    ]
).auto_panel_ids()

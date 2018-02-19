#!/usr/bin/env bash

(cd ..; sbt "docker:publishLocal")
docker build -t hmda/prometheus prometheus/.

(cd grafana; pip install -r requirements.txt)

(cd grafana;
for i in *.py
  do
    x=${i%.py}
    echo 'Generating ' $x.json
    generate-dashboard -o dashboards/$x.json $i
  done
)

docker build -t hmda/grafana grafana/.
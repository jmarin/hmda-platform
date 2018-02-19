#!/usr/bin/env bash

(cd ..; sbt "docker:publishLocal")
docker build -t hmda/prometheus prometheus/.

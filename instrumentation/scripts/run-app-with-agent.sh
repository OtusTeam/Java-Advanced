#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
AGENT=${BASEDIR}/../perf-agent/build/libs/perf-agent-1.0.0-SNAPSHOT.jar

java -javaagent:${AGENT}=com.otus.java.advanced.instrumentation.application.SomeService#process \
     -jar ${BASEDIR}/../business-app/build/libs/business-app-1.0.0-SNAPSHOT.jar


#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
TARGET_JVM_PID=7524
AGENT_FILE_PATH=${BASEDIR}/../perf-agent/build/libs/perf-agent-1.0.0-SNAPSHOT.jar

java -jar ${BASEDIR}/../agent-loader/build/libs/agent-loader-1.0.0-SNAPSHOT.jar \
     ${TARGET_JVM_PID} \
     ${AGENT_FILE_PATH} \
     com.otus.java.advanced.instrumentation.application.SomeService#process

#!/usr/bin/env bash

TARGET_JVM_PID=25468
AGENT_ARGS=com.otus.javaadvanced.controllers.HashController#hash

BASEDIR=$(dirname "$0")
AGENT_FILE_PATH=${BASEDIR}/../perf-agent/build/libs/perf-agent-1.0.0-SNAPSHOT.jar

java -jar ${BASEDIR}/../agent-loader/build/libs/agent-loader-1.0.0-SNAPSHOT.jar \
     ${TARGET_JVM_PID} \
     ${AGENT_FILE_PATH} \
     ${AGENT_ARGS}

#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
AGENT=${BASEDIR}/../perf-agent/build/libs/perf-agent-1.0.0-SNAPSHOT.jar

# LESSON why doesn't this work?
java -javaagent:${AGENT}=com.otus.javaadvanced.controllers.HashController#hash \
     -jar ${BASEDIR}/../../hash-service/build/libs/hash-service-1.0.0-SNAPSHOT.jar

#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
#     -jar ${BASEDIR}/../../hash-service/build/libs/hash-service-1.0.0-SNAPSHOT.jar

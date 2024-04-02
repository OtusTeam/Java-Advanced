#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
java -jar ${BASEDIR}/../../hash-service/build/libs/hash-service-1.0.0-SNAPSHOT.jar

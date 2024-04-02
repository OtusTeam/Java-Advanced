#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
java -jar ${BASEDIR}/../business-app/build/libs/business-app-1.0.0-SNAPSHOT.jar

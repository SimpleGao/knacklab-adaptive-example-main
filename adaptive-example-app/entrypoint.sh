#!/usr/bin/env bash

export JAVA_OPTS="-Xmx512m"
if [ "${ENABLEED}" = "true " ]; then
  java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher
else
  java org.springframework.boot.loader.JarLauncher
fi

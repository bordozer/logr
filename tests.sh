#!/bin/bash

set -euo pipefail

JAVA_HOME=/opt/java/jdk21
export PATH=$JAVA_HOME/bin:$PATH

./gradlew clean build check

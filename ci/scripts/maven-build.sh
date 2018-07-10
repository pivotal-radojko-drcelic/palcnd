#!/bin/bash
set -e

../tasks/generate-mvn-settings.sh

cd source-code || echo "missing input resource: source-code"

echo "Using MAVEN_OPTS: ${MAVEN_OPTS}"

mvn verify ${MAVEN_ARGS}

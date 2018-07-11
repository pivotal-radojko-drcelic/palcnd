#!/bin/bash
set -e

./source-code/ci/scripts/generate-mvn-settings.sh

source ./source-code/ci/scripts/common.sh

VERSION=$(build_version "./version" "number" "./source-code" $BRANCH)
echo "Version to build: ${VERSION}"

cd source-code || echo "missing input resource: source-code"

echo "Using MAVEN_OPTS: ${MAVEN_OPTS}"

mvn verify ${MAVEN_ARGS}

echo "Copying artifact to ./build "
cp target/*.jar ../build

ls ../build
cd ../build
pwd

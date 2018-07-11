#!/bin/bash

set -e

./source-code/ci/scripts/generate-settings.sh

cd acceptance-test || echo "missing input resource: acceptance-test"

APP_URI=https://${APP_HOST}.${APP_DOMAIN}

echo "Running acceptance tests against ${APP_URI}"
mvn test -DbaseURI=${APP_URI} -DappUser=${APP_USER} -DappUserPassword=${APP_USERPWD}}

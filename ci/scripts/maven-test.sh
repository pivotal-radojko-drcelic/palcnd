#!/bin/bash

set -e

./source-code/ci/scripts/generate-mvn-settings.sh

cd acceptance-test || echo "missing input resource: acceptance-test"

APP_URI=https://${APP_HOST}.${APP_DOMAIN}
USER=${APP_USER}
echo "with user: ${USER}"
PASSWORD=${APP_USERPWD}

echo "Running acceptance tests against ${APP_URI}"
mvn test -DbaseURI=${APP_URI} -DappUser=${USER} -DappUserPassword=${PASSWORD}

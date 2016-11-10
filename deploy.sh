#!/usr/bin/env sh

set -e

# build
mvn compile package

# deploy
cp target/*.war images/wildfly/standalone/deployments/
docker-compose up --build

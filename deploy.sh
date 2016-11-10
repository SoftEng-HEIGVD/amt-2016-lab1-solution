#!/usr/bin/env sh

set -e

# build
mvn clean package

# deploy
cp target/*.war images/wildfly/standalone/deployments/
docker-compose up --build

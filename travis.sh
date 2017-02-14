#!/usr/bin/env bash

if [ "$TRAVIS_TAG" != "" ]; then
    ./mvnw versions::set -DnewVersion=${TRAVIS_TAG}
fi

./mvnw install -B
echo ${TEST_VAR}
echo ${TEST_VAR_SECURE}
if [ "$TEST_VAR_SECURE" != "" ]; then
    echo "hmmja..."
fi

if [ "$TRAVIS_TAG" != "" ]; then
    ./mvnw deploy -Dmaven.javadoc.skip=true -Dgithub.global.userName=${GITHUB_USER_NAME} -Dgithub.global.password=${GITHUB_AUTH_TOKEN} -B
fi


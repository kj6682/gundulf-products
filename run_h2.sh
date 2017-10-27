#!/bin/sh

export PORT="-Dserver.port=9000"
export SPRING_PROFILE="-Dspring.profiles.active=h2"
export VERSION="1.1.0"

mvn clean install -P h2
java $PORT $JAVA_OPTS $SPRING_PROFILE -jar target/gundulf.products-$VERSION.jar


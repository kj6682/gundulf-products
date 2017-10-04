#!/bin/sh

export PORT="-Dserver.port=9000"
export SPRING_PROFILE="-Dspring.profiles.active=h2"

mvn clean install -P h2
java $PORT $JAVA_OPTS $SPRING_PROFILE -jar target/i.products-0.0.1-SNAPSHOT.jar


#!/bin/sh

export PORT="-Dserver.port=9000"
export VERSION="1.1.0"

if [ -z "$1" ]
  then
    export DATABASE_TYPE="h2"
    export SPRING_PROFILE="-Dspring.profiles.active=h2"
    export DATABASE_URL=""
elif [ "$1" == "create" ]
then
    export DATABASE_TYPE="postgres"
    export SPRING_PROFILE="-Dspring.profiles.active=postgresql, ddl-create"
    export DATABASE_URL="-Dspring.datasource.url=jdbc:postgresql://localhost:5432/cake"

elif [ "$1" == "validate" ]
then
    export DATABASE_TYPE="postgres"
    export SPRING_PROFILE="-Dspring.profiles.active=postgresql, ddl-validate"
    export DATABASE_URL="-Dspring.datasource.url=jdbc:postgresql://localhost:5432/cake"
fi

mvn clean install -P $DATABASE_TYPE
java $PORT $JAVA_OPTS $SPRING_PROFILE $DATABASE_URL -jar target/gundulf.products-$VERSION.jar
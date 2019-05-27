#!/bin/bash

waitforresponse() 
{
    HOST=$1
    PORT=$2
    while ! nc -z ${HOST} ${PORT};
    do
        echo "waiting for $HOST:$PORT";
        sleep 1;
    done;
    echo Connected in ${HOST}:${PORT}!;
}

waitforresponse $RABBITMQ_HOST $RABBITMQ_PORT

suburl=${JDBC_URL:13}
indiceDoisPontos=`expr index "$suburl" :`
hostMySql=${suburl:0:$indiceDoisPontos-1}
indiceBarra=`expr index "$suburl" /`
portaMySql=${suburl:$indiceDoisPontos:$indiceBarra-$indiceDoisPontos-1}
echo $hostMySql
echo $portaMySql

waitforresponse mysql 3306


java -Dspring.profiles.active=production -jar /app.jar
